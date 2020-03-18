package com.example.demo2.controller;

import com.example.demo2.dto.GithubUser;
import com.example.demo2.provider.GithupProvider;
import com.example.demo2.dto.AccessTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithupProvider githupProvider;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri(" https://localhost:8080/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("00ec526d656710d1e9b2");
        accessTokenDTO.setClient_secret("c4da2d38fd08fc63d0ba32db5cc70c080247ddb5");
        String accessToken = githupProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githupProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
