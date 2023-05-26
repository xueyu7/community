package com.xy.community_admin.controller;

import com.xy.community_admin.dto.DelDTO;
import com.xy.community_admin.model.User;
import com.xy.community_admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private UserService userService;

    @GetMapping("/member-list")
    public String show(Model model,
                       @RequestParam(name = "username", required = false) String username) {
        List<User> users = userService.selectList(username);
        int count = users.size();
        model.addAttribute("users", users);
        model.addAttribute("username", username);
        model.addAttribute("count", count);
        return "/member-list";
    }

    @ResponseBody
    @PostMapping("/deleteMember")
    private void delete(@RequestBody DelDTO delDTO) {
        userService.deleteById(delDTO.getId());
    }


}
