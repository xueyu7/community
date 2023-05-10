package com.xy.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends BaseMapper<Comment> {
    int incComment(Comment comment);
}
