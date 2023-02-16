package com.example.testtwtichoauthapi.security.jwt;

import com.example.testtwtichoauthapi.model.response.JwtTokenResponse;
import com.example.testtwtichoauthapi.security.TwitchUserDetailsService;
import com.example.testtwtichoauthapi.utils.TwitchRestTemplate;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET = "3F4528482B4B6250655368566D597133743677397A24432646294A404E635166";
    private final TwitchRestTemplate yandexRestTemplate;
    private final TwitchUserDetailsService twitchUserDetailsService;

    @Autowired
    public JwtService(TwitchRestTemplate yandexRestTemplate, TwitchUserDetailsService twitchUserDetailsService) {
        this.yandexRestTemplate = yandexRestTemplate;
        this.twitchUserDetailsService = twitchUserDetailsService;
    }

    public JwtTokenResponse signOrAuthenticate(String token){
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
        jwtTokenResponse.setToken(
                generateToken(twitchUserDetailsService.getOrSave(yandexRestTemplate.getTwitchUser(token))));
        return jwtTokenResponse;
    }

    public String getUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, new HashMap<>());
    }

    private String generateToken(UserDetails userDetails, Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = getUsername(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
