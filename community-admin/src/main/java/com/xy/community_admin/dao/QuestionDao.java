package com.xy.community_admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community_admin.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends BaseMapper<Question> {
    List<Question> selectDel();

    int deleteAlways(Integer id);

    int recover(Integer id);

    int deleteAll();
}
