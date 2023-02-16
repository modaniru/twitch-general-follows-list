package com.example.testtwtichoauthapi.config;

import com.example.testtwtichoauthapi.security.TwitchUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class ApplicationConfig {
    private final TwitchUserDetailsService twitchUserDetailsService;
    
    @Autowired
    public ApplicationConfig(TwitchUserDetailsService twitchUserDetailsService) {
        this.twitchUserDetailsService = twitchUserDetailsService;
    }

    @Bean
    public AuthenticationProvider provider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(twitchUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
