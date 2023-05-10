package com.xy.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.community.dao.CommentDao;
import com.xy.community.dao.QuestionDao;
import com.xy.community.dao.UserDao;
import com.xy.community.dto.CommentDTO;
import com.xy.community.enums.CommentTypeEnum;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import com.xy.community.model.Comment;
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
    private CommentDao commentDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

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
            commentDao.incComment(dbComment);
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
