package com.example.testtwtichoauthapi.model.response.template;

import lombok.Data;

import java.util.List;

@Data
public class UserDataResponse {
    private List<UserInfo> data;
}
