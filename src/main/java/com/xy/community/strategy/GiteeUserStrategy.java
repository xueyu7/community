package com.xy.community.strategy;

import com.xy.community.dto.GiteeAccessTokenDTO;
import com.xy.community.provider.dto.GiteeUser;
import com.xy.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiteeUserStrategy implements UserStrategy {

    @Autowired
    private GiteeProvider giteeProvider;
    @Autowired
    private GiteeAccessTokenDTO accessTokenDTO;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        LoginUserInfo loginUserInfo=new LoginUserInfo();
        loginUserInfo.setAvatar_url(giteeUser.getAvatar_url());
        loginUserInfo.setBio(giteeUser.getBio());
        loginUserInfo.setId(giteeUser.getId());
        loginUserInfo.setName(giteeUser.getName());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "gitee";
    }
}
