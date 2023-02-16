package com.example.testtwtichoauthapi.controller;

import com.example.testtwtichoauthapi.model.response.JwtTokenResponse;
import com.example.testtwtichoauthapi.model.request.RequestOauthToken;
import com.example.testtwtichoauthapi.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OAuthController {
    private final JwtService jwtService;

    @Autowired
    public OAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/oauth")
    public JwtTokenResponse OAuth(@RequestBody RequestOauthToken token){
        return jwtService.signOrAuthenticate(token.getToken());
    }
}
