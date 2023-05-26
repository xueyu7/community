package com.xy.community_admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xy.community_admin.dao.UserDao;
import com.xy.community_admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> selectList(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(username)) {
            wrapper.like("name", username);
        }
        List<User> users = userDao.selectList(wrapper);
        return users;
    }

    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }
}
