package com.xy.community.dao;

import com.xy.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    void insert(User user);

    User findByToken(@Param("token") String token);
}
