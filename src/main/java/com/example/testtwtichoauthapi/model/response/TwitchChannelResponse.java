package com.example.testtwtichoauthapi.model.response;

import com.example.testtwtichoauthapi.model.response.template.UserFollowResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class TwitchChannelResponse {
    private String channelName;
    private String imageLink;

    public static List<TwitchChannelResponse> toModel(List<UserFollowResponse> data){
        List<TwitchChannelResponse> res = new ArrayList<>();
        for (UserFollowResponse element : data) {
            TwitchChannelResponse twitchChannelResponse = new TwitchChannelResponse();
            twitchChannelResponse.setChannelName(element.getToName());
            twitchChannelResponse.setImageLink("todo");//todo
            res.add(twitchChannelResponse);
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwitchChannelResponse that = (TwitchChannelResponse) o;
        return channelName.equals(that.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelName);
    }
}
