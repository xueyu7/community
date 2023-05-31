package com.xy.community_admin.controller;

import com.xy.community_admin.dao.UserDao;
import com.xy.community_admin.dto.AddDTO;
import com.xy.community_admin.dto.DelDTO;
import com.xy.community_admin.dto.NotificationDTO;
import com.xy.community_admin.model.User;
import com.xy.community_admin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserDao userDao;

    @GetMapping("/notification-list")
    public String show(Model model,
                       @RequestParam(name = "select", required = false) String select,
                       @RequestParam(name = "search", required = false) String search) {
        List<NotificationDTO> notifications = notificationService.selectList(select, search);
        int count = notifications.size();
        model.addAttribute("notifications", notifications);
        model.addAttribute("count", count);
        model.addAttribute("search", search);
        model.addAttribute("select", select);
        return "/notification-list";
    }

    @ResponseBody
    @PostMapping("/deleteNotification")
    public void delete(@RequestBody DelDTO delDTO) {
        notificationService.deleteById(delDTO.getId());
    }

    @GetMapping("/notification-add")
    public String add(Model model) {
        List<User> users = userDao.selectList(null);
        model.addAttribute("users",users);
        return "/notification-add";
    }

    @ResponseBody
    @PostMapping("/addNotification")
    public void add(@RequestBody AddDTO addDTO) {
        notificationService.add(addDTO);
    }
}
