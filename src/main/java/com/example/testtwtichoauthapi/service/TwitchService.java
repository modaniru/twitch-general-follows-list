package com.example.testtwtichoauthapi.service;

import com.example.testtwtichoauthapi.model.response.TwitchChannelResponse;
import com.example.testtwtichoauthapi.model.response.template.UserInfo;
import com.example.testtwtichoauthapi.utils.TwitchApiRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TwitchService {
    @Autowired
    private TwitchApiRestTemplate twitchApiRestTemplate;

    public List<TwitchChannelResponse> getGeneralFollowingList(String firstName, String secondName) {
        List<TwitchChannelResponse> firstFollowingList = TwitchChannelResponse.toModel(twitchApiRestTemplate.getFollowingList(firstName));
        List<TwitchChannelResponse> secondFollowingList = TwitchChannelResponse.toModel(twitchApiRestTemplate.getFollowingList(secondName));
        Map<String, TwitchChannelResponse> general = new HashMap<>();
        for (TwitchChannelResponse twitchChannelResponse : firstFollowingList) {
            general.put(twitchChannelResponse.getChannelName(), twitchChannelResponse);
        }
        Map<String, TwitchChannelResponse> res = new HashMap<>();
        List<UserInfo> links;
        secondFollowingList.forEach(e -> {
            if (general.containsKey(e.getChannelName())) {
                res.put(e.getChannelName(), e);
            }
        });
        List<String> names = new ArrayList<>();
        res.forEach((k, e) ->
                names.add(k));
        links = twitchApiRestTemplate.getUserId(names);
        for (UserInfo link : links) {
            res.get(link.getDisplayName()).setImageLink(link.getProfileImageUrl());
        }
        List<TwitchChannelResponse> result = new ArrayList<>();
        for (String s : res.keySet()) {
            result.add(res.get(s));
        }
        return result;
    }
}
