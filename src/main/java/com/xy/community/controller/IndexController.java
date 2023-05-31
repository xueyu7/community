package com.xy.community.controller;

import com.xy.community.cache.HotTagCache;
import com.xy.community.dto.PaginationDTO;
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
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String hello(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "7") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "sort", required = false) String sort,
                        Model model) {
        PaginationDTO pagination = questionService.list(search, sort, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("hots", hotTagCache.getHots());
//        model.addAttribute("section", "index");
        model.addAttribute("sort", sort);
        return "index";
    }

//    @GetMapping("/{action}")
//    public String profile(@PathVariable(name = "action") String action,
//                          @RequestParam(name = "page", defaultValue = "1") Integer page,
//                          @RequestParam(name = "size", defaultValue = "5") Integer size,
//                          Model model) {
//        if (action.equals("hottest")) {
//            model.addAttribute("section", "hottest");
//            PaginationDTO paginationDTO = questionService.listByHot(page, size);
//            model.addAttribute("pagination", paginationDTO);
//        }
//        return "index";
//    }
}
