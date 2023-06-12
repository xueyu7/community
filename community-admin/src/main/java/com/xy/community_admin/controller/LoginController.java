package com.xy.community_admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community_admin.dao.AdminDao;
import com.xy.community_admin.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private AdminDao adminDao;

    @GetMapping("/login")
    public String show() {
        return "/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        HttpServletRequest request,
                        Model model) {
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username)
                    .eq("password", password);
            Admin admin = adminDao.selectOne(wrapper);
            if (admin == null) {
                model.addAttribute("error", "用户名或密码不正确");
                return "/login";
            } else {
//                Cookie cookie = new Cookie("username", username);
//                cookie.setMaxAge(60);
//                response.addCookie(cookie);
                request.getSession().setAttribute("username", username);
                request.getSession().setMaxInactiveInterval(60 * 60 * 60);
//                request.getSession().setAttribute("id",admin.getId());
                return "redirect:/";
            }
        } else {
            model.addAttribute("error", "用户名或密码不能为空");
            return "/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:login";
    }
}
