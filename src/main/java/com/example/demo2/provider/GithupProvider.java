package com.example.demo2.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo2.dto.AccessTokenDTO;
import com.example.demo2.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GithupProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        try {
            String string = post("https://github.com/login/oauth/access_token", JSON.toJSONString(accessTokenDTO));
            String token = string.split("&")[0].split("=")[1];
            return token;

        }catch (IOException e){
        }
        return null;
    }

    private String post(String url, String json) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public GithubUser getUser(String accessToken){
        try {
            return JSON.parseObject(run("https://api.github.com/user?user?access_token="+accessToken),
                    GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
