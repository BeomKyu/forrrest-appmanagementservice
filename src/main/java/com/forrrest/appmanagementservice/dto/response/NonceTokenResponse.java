package com.forrrest.appmanagementservice.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.forrrest.appmanagementservice.entity.NonceToken;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NonceTokenResponse {
    private String token;
    private String clientId;
    private Long profileId;
    private String redirectUri;
    private LocalDateTime expiresAt;

    @Builder
    public NonceTokenResponse(NonceToken nonceToken) {
        this.token = nonceToken.getToken();
        this.clientId = nonceToken.getClientId();
        this.profileId = nonceToken.getProfileId();
        this.redirectUri = nonceToken.getRedirectUri();
        this.expiresAt = nonceToken.getExpiresAt();
    }

    public static NonceTokenResponse of(NonceToken nonceToken) {
        return new NonceTokenResponse(nonceToken);
    }
}
