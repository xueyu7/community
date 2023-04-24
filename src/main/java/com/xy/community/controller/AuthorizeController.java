package com.xy.community.controller;

import com.xy.community.dao.UserDao;
import com.xy.community.dto.AccessTokenDTO;
import com.xy.community.dto.GithubUser;
import com.xy.community.model.User;
import com.xy.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private AccessTokenDTO accessTokenDTO;

    @Autowired
    private UserDao userDao;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            //写入session =>写入数据库
            userDao.insert(user);
            //写入cookie =>response里添加cookie的name+value
            response.addCookie(new Cookie("token", token));
            //登录成功，写cookie和session
//            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }


}
