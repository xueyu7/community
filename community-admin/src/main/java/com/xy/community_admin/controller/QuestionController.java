package com.xy.community_admin.controller;

import com.xy.community_admin.dto.DelDTO;
import com.xy.community_admin.dto.QuestionDTO;
import com.xy.community_admin.model.Question;
import com.xy.community_admin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question-list")
    public String show(Model model,
                       @RequestParam(name = "select", required = false) String select,
                       @RequestParam(name = "search", required = false) String search) {
        List<QuestionDTO> questions = questionService.selectList(select, search);
        int count = questions.size();
        model.addAttribute("questions", questions);
        model.addAttribute("count", count);
        model.addAttribute("search", search);
        model.addAttribute("select", select);
        return "/question-list";
    }

    @ResponseBody
    @PostMapping("/deleteQuestion")
    private void delete(@RequestBody DelDTO delDTO) {
        questionService.deleteById(delDTO.getId());
    }

    @GetMapping("/question-del")
    public String del(Model model) {
        List<Question> questions = questionService.selectDel();
        int count = questions.size();
        model.addAttribute("questions", questions);
        model.addAttribute("count", count);
        return "/question-del";
    }

    @ResponseBody
    @PostMapping("/deleteQuestionAlways")
    private void deleteAlways(@RequestBody DelDTO delDTO) {
        questionService.deleteAlways(delDTO.getId());
    }

    @ResponseBody
    @PostMapping("/recover")
    private void recover(@RequestBody DelDTO delDTO) {
        questionService.recover(delDTO.getId());
    }

    @ResponseBody
    @PostMapping("/deleteAll")
    private void deleteAll() {
        questionService.deleteAll();
    }


}
