package com.xy.community.dao;

import com.xy.community.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    void insert(User user);
}
