package com.example.testtwtichoauthapi.repository;

import com.example.testtwtichoauthapi.model.TwitchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TwitchUserRepository extends JpaRepository<TwitchUser, String> {
    Optional<TwitchUser> findTwitchUserByUsername(String username);
}
