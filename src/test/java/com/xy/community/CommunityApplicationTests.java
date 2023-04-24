package com.xy.community;

import com.xy.community.dao.UserDao;
import com.xy.community.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class CommunityApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        User user=new User();
        user.setAccountId("123");
        user.setName("Hyunjin");
        user.setToken("12321");
        user.setId(123);
        userDao.insert(user);
    }

    @Test
    void test1(){
        User user=new User();
        user.setName("lee");
        userDao.updateById(user);
    }
}
