package com.example.testtwtichoauthapi.controller;

import com.example.testtwtichoauthapi.model.response.TwitchChannelResponse;
import com.example.testtwtichoauthapi.service.TwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/twitch")
public class TwitchController {

    @Autowired
    private TwitchService twitchService;

    @GetMapping("/general-following-list")
    public List<TwitchChannelResponse> getGeneralFollowingList(
            @RequestParam(name = "first-user") String firstUser,
            @RequestParam(name = "second-user") String secondUser){
        return twitchService.getGeneralFollowingList(firstUser, secondUser);
    }
}
