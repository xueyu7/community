package com.xy.community.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStrategyFactory {
    @Autowired
    private List<UserStrategy> userStrategies;

    public UserStrategy getStrategy(String type){
        for (UserStrategy userStrategy : userStrategies) {
            if (userStrategy.getSupportedType().equals(type)){
                return userStrategy;
            }
        }
        return null;
    }
}
