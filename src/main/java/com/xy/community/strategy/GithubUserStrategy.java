package com.xy.community.strategy;

import com.xy.community.dto.GithubAccessTokenDTO;
import com.xy.community.provider.dto.GithubUser;
import com.xy.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubUserStrategy implements UserStrategy {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GithubAccessTokenDTO accessTokenDTO;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        LoginUserInfo loginUserInfo=new LoginUserInfo();
        loginUserInfo.setAvatar_url(githubUser.getAvatar_url());
        loginUserInfo.setBio(githubUser.getBio());
        loginUserInfo.setId(githubUser.getId());
        loginUserInfo.setName(githubUser.getName());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "github";
    }
}
