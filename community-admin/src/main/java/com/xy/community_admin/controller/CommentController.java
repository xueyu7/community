package com.xy.community_admin.controller;

import com.xy.community_admin.dto.CommentDTO;
import com.xy.community_admin.dto.DelDTO;
import com.xy.community_admin.dto.QuestionDTO;
import com.xy.community_admin.model.Question;
import com.xy.community_admin.service.CommentService;
import com.xy.community_admin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comment-list")
    public String show(Model model,
                       @RequestParam(name = "select", required = false) String select,
                       @RequestParam(name = "search", required = false) String search) {
        List<CommentDTO> comments = commentService.selectList(select, search);
        int count = comments.size();
        model.addAttribute("comments", comments);
        model.addAttribute("count", count);
        model.addAttribute("search", search);
        model.addAttribute("select", select);
        return "/comment-list";
    }

    @ResponseBody
    @PostMapping("/deleteComment")
    public void delete(@RequestBody DelDTO delDTO) {
        commentService.deleteById(delDTO.getId());
    }

}
