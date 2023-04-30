package com.xy.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.community.dao.UserDao;
import com.xy.community.dto.AccessTokenDTO;
import com.xy.community.dto.GiteeUser;
import com.xy.community.model.User;
import com.xy.community.provider.GiteeProvider;
import com.xy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private AccessTokenDTO accessTokenDTO;

    @Autowired
    private UserService userService;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);

        if (giteeUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(giteeUser.getName());
            user.setAccountId(String.valueOf(giteeUser.getId()));
            user.setAvatarUrl(giteeUser.getAvatar_url());
//            登录成功，写cookie和session
            //写入session => 写入数据库：userDao.insert(user);
            userService.createOrUpdate(user);
            //写入cookie =>  response里添加cookie的 name+value
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }


}
