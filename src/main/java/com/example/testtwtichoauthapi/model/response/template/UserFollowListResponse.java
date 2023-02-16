package com.example.testtwtichoauthapi.model.response.template;

import com.example.testtwtichoauthapi.model.Pagination;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserFollowListResponse {
    private Integer total;
    private List<UserFollowResponse> data;
    private Pagination pagination;
}
