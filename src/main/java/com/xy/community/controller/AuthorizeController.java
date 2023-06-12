package com.xy.community.controller;

import com.xy.community.model.User;
import com.xy.community.service.UserService;
import com.xy.community.strategy.LoginUserInfo;
import com.xy.community.strategy.UserStrategy;
import com.xy.community.strategy.UserStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private UserStrategyFactory userStrategyFactory;

    @Autowired
    private UserService userService;

    @GetMapping("callback/{type}")
    public String callback(@PathVariable(name = "type") String type,
                           @RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        UserStrategy userStrategy = userStrategyFactory.getStrategy(type);
        LoginUserInfo loginUserInfo = userStrategy.getUser(code, state);
        if (loginUserInfo != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(loginUserInfo.getName());
            user.setAccountId(String.valueOf(loginUserInfo.getId()));
            user.setAvatarUrl(loginUserInfo.getAvatar_url());
            user.setType(type);
            if (user.getAccountId() != "null") {
                userService.createOrUpdate(user);
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
            }
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


//    @GetMapping("callback")
//    public String callback(@RequestParam(name = "code") String code,
//                           @RequestParam(name = "state") String state,
//                           HttpServletResponse response,
//                           HttpServletRequest request) {
//        accessTokenDTO.setCode(code);
//        accessTokenDTO.setState(state);
//        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
//        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
//
//        if (giteeUser != null) {
//            User user = new User();
//            String token = UUID.randomUUID().toString();
//            user.setToken(token);
//            user.setName(giteeUser.getName());
//            user.setAccountId(String.valueOf(giteeUser.getId()));
//            user.setAvatarUrl(giteeUser.getAvatar_url());
////            登录成功，写cookie和session
//            //写入session => 写入数据库：userDao.insert(user);
//            userService.createOrUpdate(user);
//            //写入cookie =>  response里添加cookie的 name+value
//            response.addCookie(new Cookie("token", token));
//            return "redirect:/";
//        } else {
//            //登录失败，重新登录
//            return "redirect:/";
//        }
//    }
}
