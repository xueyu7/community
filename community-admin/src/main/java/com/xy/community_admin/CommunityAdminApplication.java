package com.xy.community_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xy.community_admin.dao")
public class CommunityAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityAdminApplication.class, args);
    }

}
