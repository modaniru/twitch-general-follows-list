package com.example.testtwtichoauthapi.model.response.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TwitchUserInformationResponse {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("login")
    private String login;
    @JsonProperty("scopes")
    private List<String> scopes;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("expires_in")
    private Long expiresIn;
}
