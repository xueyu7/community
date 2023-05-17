package com.xy.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.community.dao.QuestionDao;
import com.xy.community.dto.PaginationDTO;
import com.xy.community.dto.QuestionDTO;
import com.xy.community.model.Question;
import com.xy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HotTagController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/hottag/{tag}")
    public String question(@RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "5") Integer size,
                           @RequestParam(name = "hot_search", required = false) String hot_search,
                           @PathVariable(name = "tag") String tag,
                           Model model) {
        PaginationDTO pagination = questionService.list(hot_search, page, size, tag);
        model.addAttribute("pagination", pagination);
        List<Question> hotQuestions = questionService.selectHot(tag);
        model.addAttribute("hotQuestions", hotQuestions);
        model.addAttribute("tag", tag);
        model.addAttribute("hot_search", hot_search);
        return "hottag";
    }
}
