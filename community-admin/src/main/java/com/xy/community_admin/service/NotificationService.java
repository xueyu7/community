package com.xy.community_admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community_admin.dao.NotificationDao;
import com.xy.community_admin.dao.UserDao;
import com.xy.community_admin.dto.AddDTO;
import com.xy.community_admin.dto.NotificationDTO;
import com.xy.community_admin.enums.NotificationTypeEnum;
import com.xy.community_admin.model.Notification;
import com.xy.community_admin.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private UserDao userDao;

    public List<NotificationDTO> selectList(String select, String search) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search) || StringUtils.isNotEmpty(select)) {
            if (select.equals("all")) {
                wrapper.like("outer_title", search);
            }
            if (select.equals("question")) {
                wrapper.eq("type", 1).like("outer_title", search);
            }
            if (select.equals("comment")) {
                wrapper.eq("type", 2).like("outer_title", search);
            }
            if (select.equals("sys")) {
                wrapper.eq("type", 3).like("outer_title", search);
            }
            if (select.equals("read")) {
                wrapper.eq("status", 1).like("outer_title", search);
            }
            if (select.equals("unread")) {
                wrapper.eq("status", 0).like("outer_title", search);
            }
        }
        List<Notification> notifications = notificationDao.selectList(wrapper);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notifications) {
            User user = userDao.selectById(notification.getReceiver());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));
            notificationDTO.setUser(user);
            notificationDTOList.add(notificationDTO);
        }
        return notificationDTOList;
    }

    public void deleteById(Integer id) {
        notificationDao.deleteById(id);
    }

    public void add(AddDTO addDTO) {
        Notification notification=new Notification();
        notification.setNotifier(addDTO.getId());
        notification.setNotifierName(addDTO.getName());
        notification.setOuterTitle(addDTO.getContent());
        notification.setReceiver(addDTO.getReceiver());
        notification.setType(NotificationTypeEnum.REPLY_SYS.getType());
        notificationDao.insert(notification);
    }
}
