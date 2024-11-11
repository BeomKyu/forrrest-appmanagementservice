package com.forrrest.appmanagementservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "nonce_tokens")
public class NonceToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 700)
    private String token;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false)
    private String redirectUri;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    @Builder
    public NonceToken(String token, String clientId, Long profileId, 
        String redirectUri, LocalDateTime expiresAt) {
        this.token = token;
        this.clientId = clientId;
        this.profileId = profileId;
        this.redirectUri = redirectUri;
        this.expiresAt = expiresAt;
    }

    public void markAsUsed() {
        this.used = true;
    }
}
