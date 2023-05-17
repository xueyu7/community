package com.xy.community.strategy;

public interface UserStrategy {
    LoginUserInfo getUser(String code,String state);
    String getSupportedType();
}
