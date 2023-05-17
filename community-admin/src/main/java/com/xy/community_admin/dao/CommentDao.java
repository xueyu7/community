package com.xy.community_admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community_admin.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends BaseMapper<Comment> {
    int incComment(Comment comment);
}
