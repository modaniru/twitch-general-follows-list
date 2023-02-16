package com.example.testtwtichoauthapi.model.response.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserInfo {
    private String id;
    private String login;
    @JsonProperty("display_name")
    private String displayName;
    private String type;
    @JsonProperty("broadcaster_type")
    private String broadcasterType;
    private String description;
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    @JsonProperty("offline_image_url")
    private String offlineImageUrl;
    @JsonProperty("view_count")
    private Integer viewCount;
    @JsonProperty("created_at")
    private Date createdAt;
}
