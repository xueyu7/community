package com.xy.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao extends BaseMapper<Question> {
}
