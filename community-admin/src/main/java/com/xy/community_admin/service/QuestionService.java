package com.xy.community_admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community_admin.dao.QuestionDao;
import com.xy.community_admin.dao.UserDao;
import com.xy.community_admin.dto.QuestionDTO;
import com.xy.community_admin.model.Question;
import com.xy.community_admin.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private UserDao userDao;

    public List<QuestionDTO> selectList(String select, String search) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search) || StringUtils.isNotEmpty(select)) {
            if (!select.equals("creator")){
                wrapper.like(select, search);
            }
        }
        List<Question> questions = questionDao.selectList(wrapper);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userDao.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        if (StringUtils.isNotEmpty(select)&&select.equals("creator")){
            questionDTOList = questionDTOList.stream().filter(questionDTO -> questionDTO.getUser().getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        }
        return questionDTOList;
    }

    public void deleteById(Integer id) {
        questionDao.deleteById(id);
    }

    public List<Question> selectDel() {
        List<Question> questions = questionDao.selectDel();
        return questions;
    }

    public void deleteAlways(Integer id) {
        questionDao.deleteAlways(id);
    }

    public void recover(Integer id) {
        questionDao.recover(id);
    }

    public void deleteAll() {
        questionDao.deleteAll();
    }
}
