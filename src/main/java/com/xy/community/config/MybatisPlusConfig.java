package com.xy.community.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.xy.community.dao")
@Configuration
public class MybatisPlusConfig {
}
