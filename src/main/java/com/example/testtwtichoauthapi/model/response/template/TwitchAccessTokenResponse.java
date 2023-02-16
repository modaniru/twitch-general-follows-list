package com.example.testtwtichoauthapi.model.response.template;

import lombok.Data;

@Data
public class TwitchAccessTokenResponse {
    private String access_token;
    private Integer expires_in;
    private String token_type;
}
