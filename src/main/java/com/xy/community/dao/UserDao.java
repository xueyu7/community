package com.xy.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

    User findByToken(@Param("token") String token);

    User findByAccountId(String accountId);
}
