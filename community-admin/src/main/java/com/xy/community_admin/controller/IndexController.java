package com.xy.community_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/welcome")
    public String show(){
        return "/welcome";
    }
}
