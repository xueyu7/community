package com.xy.community.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community.dto.CommentCreateDTO;
import com.xy.community.dto.CommentDTO;
import com.xy.community.dto.ResultDTO;
import com.xy.community.enums.CommentTypeEnum;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import com.xy.community.model.Comment;
import com.xy.community.model.User;
import com.xy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isEmpty(commentCreateDTO.getContent())) {
            return new CustomizeException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }


    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO comments(@PathVariable(name = "id") Integer id) {
        List<CommentDTO> commentDTOS = commentService.listByParentId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }

}
