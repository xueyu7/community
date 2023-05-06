package com.xy.community.service;

import com.xy.community.dao.CommentDao;
import com.xy.community.dao.QuestionDao;
import com.xy.community.enums.CommentTypeEnum;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import com.xy.community.model.Comment;
import com.xy.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private QuestionDao questionDao;

    @Transactional
    public void insert(Comment comment) {
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
            commentDao.insert(comment);
        } else {
            //回复问题
            Question question = questionDao.selectById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentDao.insert(comment);
            questionDao.incComment(question);
        }
    }
}
