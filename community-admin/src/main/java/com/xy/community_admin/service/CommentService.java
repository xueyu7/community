package com.xy.community_admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community_admin.dao.CommentDao;
import com.xy.community_admin.dao.UserDao;
import com.xy.community_admin.dto.CommentDTO;
import com.xy.community_admin.model.Comment;
import com.xy.community_admin.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    public List<CommentDTO> selectList(String select, String search) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search) || StringUtils.isNotEmpty(select)) {
            if (select.equals("content")) {
                wrapper.like(select, search);
            }
            if (select.equals("first")) {
                wrapper.eq("type", 1).like("content", search);
            }
            if (select.equals("second")) {
                wrapper.eq("type", 2).like("content", search);
            }
        }
        List<Comment> comments = commentDao.selectList(wrapper);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userDao.selectById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        if (StringUtils.isNotEmpty(select) && select.equals("name")) {
            commentDTOList = commentDTOList.stream().filter(commentDTO -> commentDTO.getUser().getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        }
        return commentDTOList;
    }

    public void deleteById(Integer id) {
        commentDao.deleteById(id);
    }
}
