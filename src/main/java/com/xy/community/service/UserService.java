package com.xy.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xy.community.dao.UserDao;
import com.xy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void createOrUpdate(User user) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("account_id",user.getAccountId());
        wrapper.eq("type",user.getType());
        User dbUser = userDao.selectOne(wrapper);
        if (dbUser==null){
            userDao.insert(user);
        }else {
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            userDao.updateById(dbUser);
        }

    }
}
