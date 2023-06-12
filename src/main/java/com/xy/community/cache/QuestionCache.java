package com.xy.community.cache;

import com.xy.community.dao.QuestionDao;
import com.xy.community.dao.UserDao;
import com.xy.community.dto.QuestionDTO;
import com.xy.community.model.Question;
import com.xy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionCache {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private UserDao userDao;

    public List<QuestionDTO> getStickies() {
        List<Question> questions = questionDao.selectSticky();
        if (questions != null && questions.size() != 0) {
            List<QuestionDTO> questionDTOS = new ArrayList<>();
            for (Question question : questions) {
                User user = userDao.selectById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTO.setDescription("");
                questionDTOS.add(questionDTO);
            }
            return questionDTOS;
        }
        return new ArrayList<>();
    }
}
