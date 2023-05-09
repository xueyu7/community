package com.xy.community.controller;

import com.xy.community.dto.CommentDTO;
import com.xy.community.dto.QuestionDTO;
import com.xy.community.enums.CommentTypeEnum;
import com.xy.community.service.CommentService;
import com.xy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model) {
        QuestionDTO questionDTO = questionService.selectById(id);
        List<CommentDTO> comments = commentService.listByParentId(id, CommentTypeEnum.QUESTION);

        //累加阅读数——高并发问题
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        return "question";
    }
}
