package com.xy.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.community.dao.CommentDao;
import com.xy.community.dao.NotificationDao;
import com.xy.community.dao.QuestionDao;
import com.xy.community.dao.UserDao;
import com.xy.community.dto.CommentDTO;
import com.xy.community.enums.CommentTypeEnum;
import com.xy.community.enums.NotificationStatusEnum;
import com.xy.community.enums.NotificationTypeEnum;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import com.xy.community.model.Comment;
import com.xy.community.model.Notification;
import com.xy.community.model.Question;
import com.xy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentDao.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionDao.selectById(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentDao.insert(comment);
            commentDao.incComment(dbComment);
            //创建通知
            createNotify(comment, commentator.getName(), question.getTitle(), dbComment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        } else {
            //回复问题
            Question question = questionDao.selectById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentDao.insert(comment);
            questionDao.incComment(question);
            //创建通知
            createNotify(comment, commentator.getName(), question.getTitle(), question.getCreator(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, String notifierName, String outTitle, Integer receiver, NotificationTypeEnum notificationType, Integer outerId) {
        if (receiver.equals(comment.getCommentator())) {
            return;
        } else {
            Notification notification = new Notification();
            notification.setNotifier(comment.getCommentator());
            notification.setNotifierName(notifierName);
            notification.setReceiver(receiver);
            notification.setType(notificationType.getType());
            notification.setOuterId(outerId);
            notification.setOuterTitle(outTitle);
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationDao.insert(notification);
        }

    }

    public List<CommentDTO> listByParentId(Integer id, CommentTypeEnum typeEnum) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id)
                .eq("type", typeEnum.getType())
                .orderByDesc("gmt_create");
        List<Comment> comments = commentDao.selectList(wrapper);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userDao.selectById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
