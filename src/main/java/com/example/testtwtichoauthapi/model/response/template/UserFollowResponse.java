package com.example.testtwtichoauthapi.model.response.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserFollowResponse {
    @JsonProperty("from_id")
    private String fromId;
    @JsonProperty("from_login")
    private String fromLogin;
    @JsonProperty("from_name")
    private String fromName;
    @JsonProperty("to_id")
    private String toId;
    @JsonProperty("to_login")
    private String toLogin;
    @JsonProperty("to_name")
    private String toName;
    @JsonProperty("followed_at")
    private Date followedAt;
}
