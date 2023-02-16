package com.example.testtwtichoauthapi.utils;

import com.example.testtwtichoauthapi.model.response.template.TwitchUserInformationResponse;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TwitchRestTemplate {
    private final RestTemplate restTemplate = new RestTemplate();

    public TwitchUserInformationResponse getTwitchUser(String token) {
        System.out.println("get twitch user start");
        TwitchUserInformationResponse body = restTemplate.exchange(
                "https://id.twitch.tv/oauth2/validate",
                HttpMethod.GET,
                new HttpEntity<>(getOAuthHeader(token)),
                TwitchUserInformationResponse.class
        ).getBody();
        System.out.println("get twitch user end: " + LocalDateTime.now());
        return body;
    }

    private HttpHeaders getOAuthHeader(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "OAuth " + token);
        return httpHeaders;
    }
}
