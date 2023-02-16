package com.example.testtwtichoauthapi.controller;

import com.example.testtwtichoauthapi.security.TwitchUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SecurityPingController {
    @GetMapping("/secure-ping")
    public String securePing(){
        return "secure ping";
    }

    @GetMapping("/me")
    public TwitchUserDetails getInfo(){
        TwitchUserDetails user = (TwitchUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
