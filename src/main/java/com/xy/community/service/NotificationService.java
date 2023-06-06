package com.xy.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.community.dao.NotificationDao;
import com.xy.community.dto.NotificationDTO;
import com.xy.community.dto.PaginationDTO;
import com.xy.community.enums.NotificationStatusEnum;
import com.xy.community.enums.NotificationTypeEnum;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import com.xy.community.model.Notification;
import com.xy.community.model.User;
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
    private QuestionService questionService;

    public PaginationDTO list(Integer page, Integer size, Integer id) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("receiver", id);
        Integer count = notificationDao.selectCount(wrapper);
        Integer offset = questionService.paginationProcess(paginationDTO, count, page, size);
        wrapper.last("limit " + offset + "," + size)
                .orderByDesc("gmt_create");
        List<Notification> notifications = notificationDao.selectList(wrapper);

        if (notifications.size() == 0) {
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public Notification list(String id) {
        Notification notification = notificationDao.selectById(id);
        return notification;
    }

    public Integer unreadCount(Integer userId) {
        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0)
                .eq("receiver", userId);
        Integer count = notificationDao.selectCount(wrapper);
        return count;
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationDao.selectById(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationDao.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOf(notification.getType()));
        return notificationDTO;
    }

}
