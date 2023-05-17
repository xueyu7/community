package com.xy.community_admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.community_admin.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {
}
