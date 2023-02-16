package com.example.testtwtichoauthapi.model;

import com.example.testtwtichoauthapi.utils.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table
@Data
public class TwitchUser {
    @Id
    private String id;
    @Column(unique = true, nullable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
}
