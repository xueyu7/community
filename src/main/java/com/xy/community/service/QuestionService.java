package com.xy.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community.dao.QuestionDao;
import com.xy.community.dao.UserDao;
import com.xy.community.dto.PaginationDTO;
import com.xy.community.dto.QuestionDTO;
import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import com.xy.community.model.Question;
import com.xy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private QuestionDao questionDao;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count = questionDao.selectCount(null);
        Integer offset = paginationProcess(paginationDTO, count, page, size);
        QueryWrapper<Question> wrapper=new QueryWrapper<>();
        wrapper.last("limit "+offset+","+size)
                .orderByDesc("gmt_create");
        List<Question> questions = questionDao.selectList(wrapper);
//        List<Question> questions = questionDao.list(offset, size);
        copyToQuestionDTO(paginationDTO, questions);
        return paginationDTO;
    }

    public PaginationDTO list(Integer page, Integer size, Integer id) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator", id);
        Integer count = questionDao.selectCount(wrapper);
        Integer offset = paginationProcess(paginationDTO, count, page, size);
        wrapper.last("limit "+offset+","+size)
                .orderByDesc("gmt_create");
        List<Question> questions = questionDao.selectList(wrapper);
//        List<Question> questions = questionDao.listByUserId(id, offset, size);
        copyToQuestionDTO(paginationDTO, questions);
        return paginationDTO;
    }

    public Integer paginationProcess(PaginationDTO paginationDTO, Integer count, Integer page, Integer size) {
        paginationDTO.setPagination(count, size, page);
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        Integer offset = (page - 1) * size;
        return offset;
    }

    private void copyToQuestionDTO(PaginationDTO paginationDTO, List<Question> questions) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userDao.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
    }


    public QuestionDTO selectById(Integer id) {
        Question question = questionDao.selectById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userDao.selectById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionDao.insert(question);
        } else {
            int update = questionDao.updateById(question);
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        questionDao.incView(question);
        //高并发问题
//        Question question = questionDao.selectById(id);
//        question.setViewCount(question.getViewCount()+1);
//        questionDao.updateById(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isEmpty(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = String.join("|", tags);
        System.out.println(regexpTag);
        Question question =new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionDao.selectRelated(question);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question q : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
