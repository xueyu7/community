package com.xy.community.controller;

import com.xy.community.dto.PaginationDTO;
import com.xy.community.model.Notification;
import com.xy.community.model.User;
import com.xy.community.service.NotificationService;
import com.xy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if (action.equals("questions")) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(page, size, user.getId());
            model.addAttribute("pagination", paginationDTO);
        } else if (action.equals("replies")) {
            //访问时更新——管理端新增消息通知
            Integer unreadCount = notificationService.unreadCount(user.getId());
            request.getSession().setAttribute("unreadCount", unreadCount);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            PaginationDTO paginationDTO = notificationService.list(page, size, user.getId());
            model.addAttribute("pagination", paginationDTO);
        }
        return "profile";
    }

    @GetMapping("/sys/{id}")
    public String profile(@PathVariable(name = "id") String id,
                          Model model) {
        Notification notification = notificationService.list(id);
        model.addAttribute("notification", notification);
        return "sys";
    }
}
