package com.xy.community.service;

import com.xy.community.dao.UserDao;
import com.xy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void createOrUpdate(User user) {
        User dbUser = userDao.findByAccountId(user.getAccountId());
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
