package com.example.testtwtichoauthapi.utils;

import com.example.testtwtichoauthapi.model.response.template.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TwitchApiRestTemplate {
    @Value("${twitch.client_secret}")
    private String clientSecret;
    @Value("${twitch.client_id}")
    private String clientId;
    @Value("${twitch.url.get_user_info}")
    private String getUserUrl;
    @Value("${twitch.url.get_user_following_list}")
    private String getUserFollowingListUrl;
    @Value("${twitch.url.get_token}")
    private String getTokenUrl;
    @Value("${twitch.url.validate_token}")
    private String validateUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private TwitchAccessTokenResponse twitchAccessTokenResponse;

    public List<UserFollowResponse> getFollowingList(String username){
        getAppToken();
        UserInfo user = getUserId(List.of(username)).get(0);
        String url = UriComponentsBuilder.fromHttpUrl(getUserFollowingListUrl)
                .queryParam("from_id", user.getId())
                .encode().toUriString();
        UserFollowListResponse model = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeadersAuthAndClientId()),
                UserFollowListResponse.class).getBody();
        List<UserFollowResponse> userFollowResponse = new ArrayList<>(model.getData());
        while (model.getPagination().getCursor()!=null && !model.getPagination().getCursor().isEmpty()){
            url = UriComponentsBuilder.fromHttpUrl(getUserFollowingListUrl)
                    .queryParam("from_id", user.getId())
                    .queryParam("after", model.getPagination().getCursor())
                    .encode().toUriString();
            model = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeadersAuthAndClientId()),
                    UserFollowListResponse.class).getBody();
            userFollowResponse.addAll(model.getData());
        }
        return userFollowResponse;
        //todo цикл патогнации(страниц)
    }

    public List<UserInfo> getUserId(List<String> usernames){
        UriComponentsBuilder uriComponentsBuilder;
        List<UserInfo> res = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            if(i%40==0 && i!=0){
                uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getUserUrl);
                for (String username : temp) {
                    uriComponentsBuilder.queryParam("login", username);
                }
                String url = uriComponentsBuilder
                        .queryParam("client_id", clientId)
                        .encode().toUriString();

                temp = new ArrayList<>();
                System.out.println(url);
                res.addAll(restTemplate.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(getHeadersAuthAndClientId()),
                        UserDataResponse.class).getBody().getData());
            }
            temp.add(usernames.get(i));
        }
        if(!usernames.isEmpty()){
            uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getUserUrl);
            for (String username : temp) {
                uriComponentsBuilder.queryParam("login", username);
            }
            String url = uriComponentsBuilder
                    .encode().toUriString();

            res.addAll(restTemplate.exchange(url,
                    HttpMethod.GET,
                    new HttpEntity<>(getHeadersAuthAndClientId()),
                    UserDataResponse.class).getBody().getData());
        }
        return res;
    }

    private TwitchAccessTokenResponse getAppToken(){
        if(twitchAccessTokenResponse != null){
            if(isActive()) return twitchAccessTokenResponse;
        }
        //todo generate url when start
        String url = UriComponentsBuilder.fromHttpUrl(getTokenUrl)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "client_credentials")
                .encode().toUriString();
        twitchAccessTokenResponse = restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(new HttpHeaders()),
                TwitchAccessTokenResponse.class).getBody();
        return twitchAccessTokenResponse;
    }

    private boolean isActive(){
        if(twitchAccessTokenResponse == null) return false;
        HttpStatusCode statusCode = restTemplate.exchange(validateUrl,
                HttpMethod.GET,
                new HttpEntity<>(getHeadersAuth()),
                String.class).getStatusCode();
        return statusCode.is2xxSuccessful();
    }

    private HttpHeaders getHeadersAuth(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + twitchAccessTokenResponse.getAccess_token());
        return httpHeaders;
    }

    private HttpHeaders getHeadersAuthAndClientId(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + twitchAccessTokenResponse.getAccess_token());
        httpHeaders.set("Client-Id", clientId);
        return httpHeaders;
    }
}
