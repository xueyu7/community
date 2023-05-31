package com.xy.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends BaseMapper<Question> {

    int incView(Question question);

    int incComment(Question question);

    List<Question> selectRelated(Question question);

    List<Question> selectSticky();
}
