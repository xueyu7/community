package com.xy.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends BaseMapper<Question> {

    List<Question> list(Integer offset, Integer size);

    List<Question> listByUserId(Integer offset, Integer size, Integer id);
}
