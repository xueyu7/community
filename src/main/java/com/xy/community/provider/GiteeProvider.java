package com.xy.community.provider;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xy.community.dto.AccessTokenDTO;
import com.xy.community.dto.GiteeUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
//        String url = "https://gitee.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";
//        url=String.format(url,accessTokenDTO.getClient_id(),accessTokenDTO.getRedirect_uri());
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&" +
                        "code=" + accessTokenDTO.getCode() + "&" +
                        "client_id=" + accessTokenDTO.getClient_id() + "&" +
                        "redirect_uri=" + accessTokenDTO.getRedirect_uri() + "&" +
                        "client_secret=" + accessTokenDTO.getClient_secret())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
//            JSONObject jsonObject = JSON.parseObject(str);
//            return jsonObject.getString("access_token");
            String token = str.split("\"")[3];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            System.out.println(str);
            GiteeUser giteeUser = JSON.parseObject(str, GiteeUser.class);    //ctrl+ALT+V
            return giteeUser;
        } catch (IOException e) {
        }
        return null;
    }

}
