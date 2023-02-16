package com.example.testtwtichoauthapi.security;

import com.example.testtwtichoauthapi.model.response.template.TwitchUserInformationResponse;
import com.example.testtwtichoauthapi.model.TwitchUser;
import com.example.testtwtichoauthapi.repository.TwitchUserRepository;
import com.example.testtwtichoauthapi.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TwitchUserDetailsService implements UserDetailsService {
    private final TwitchUserRepository twitchUserRepository;

    @Autowired
    public TwitchUserDetailsService(TwitchUserRepository twitchUserRepository) {
        this.twitchUserRepository = twitchUserRepository;
    }

    public TwitchUserDetails getOrSave(TwitchUserInformationResponse twitchUserInformationResponse){
        TwitchUser user = twitchUserRepository.findTwitchUserByUsername(twitchUserInformationResponse.getLogin()).orElse(null);
        if(user == null){
            user = new TwitchUser();
            user.setId(twitchUserInformationResponse.getUserId());
            user.setRole(Role.USER);
            user.setUsername(twitchUserInformationResponse.getLogin());
            twitchUserRepository.save(user);
        }
        TwitchUserDetails twitchUserDetails = new TwitchUserDetails();
        twitchUserDetails.setId(user.getId());
        twitchUserDetails.setUsername(user.getUsername());
        twitchUserDetails.setRole(user.getRole());
        return twitchUserDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TwitchUser twitchUser = twitchUserRepository.findTwitchUserByUsername(username)
                .orElseThrow(IllegalArgumentException::new); //todo custom Exception Handler
        TwitchUserDetails userDetails = new TwitchUserDetails();//todo вынести в отдельный mapper
        userDetails.setId(twitchUser.getId());
        userDetails.setUsername(twitchUser.getUsername());
        userDetails.setRole(twitchUser.getRole());
        return userDetails;
    }
}
