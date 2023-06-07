package com.xy.community.controller;

import com.xy.community.dto.NotificationDTO;
import com.xy.community.enums.NotificationTypeEnum;
import com.xy.community.model.User;
import com.xy.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        //读完之后直接更新session里的未读数——读完消息后不更新消息数bug
        Integer unreadCount = notificationService.unreadCount(user.getId());
        request.getSession().setAttribute("unreadCount", unreadCount);
        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else if (NotificationTypeEnum.REPLY_SYS.getType() == notificationDTO.getType()) {
            return "redirect:/sys/" + id;
        } else {
            return "redirect:/";
        }
    }
}
