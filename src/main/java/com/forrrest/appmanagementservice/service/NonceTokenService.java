package com.forrrest.appmanagementservice.service;

import com.forrrest.appmanagementservice.dto.request.NonceTokenRequest;
import com.forrrest.appmanagementservice.dto.request.NonceTokenValidationRequest;
import com.forrrest.appmanagementservice.dto.response.NonceTokenResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.entity.AppConnection;
import com.forrrest.appmanagementservice.entity.NonceToken;
import com.forrrest.appmanagementservice.exception.AppErrorCode;
import com.forrrest.appmanagementservice.exception.AppException;
import com.forrrest.appmanagementservice.exception.UnauthorizedAccessException;
import com.forrrest.appmanagementservice.repository.AppConnectionRepository;
import com.forrrest.appmanagementservice.repository.AppRepository;
import com.forrrest.appmanagementservice.repository.NonceTokenRepository;
import com.forrrest.appmanagementservice.util.SecurityUtils;
import com.forrrest.common.security.token.JwtTokenProvider;
import com.forrrest.common.security.token.TokenType;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NonceTokenService {

    private final NonceTokenRepository nonceTokenRepository;
    private final AppConnectionRepository appConnectionRepository;
    private final AppRepository appRepository;
    private final JwtTokenProvider tokenProvider;

    @Value("${token.validity.NONCE}")
    private long nonceTokenValidity;

    @Transactional
    public NonceTokenResponse createNonceToken(NonceTokenRequest request) {
        AppConnection connection = appConnectionRepository.findById(request.getConnectionId())
                .orElseThrow(() -> new AppException(AppErrorCode.CONNECTION_NOT_FOUND));

        // 연결 소유권 검증
        Long profileId = SecurityUtils.getCurrentProfileId();
        if (!connection.getProfileId().equals(profileId)) {
            throw new AppException(AppErrorCode.NOT_CONNECTION_OWNER);
        }

        App app = connection.getApp();
        String profileName = SecurityUtils.getCurrentProfileName();

        // JWT 토큰 생성을 위한 claims 설정

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", profileName);
        claims.put("clientId", app.getClientId());
        claims.put("connectionId", connection.getId());
        claims.put("redirectUri", app.getRedirectUri());

        try {
            SecurityUtils.validateAppOwnership(app);
            claims.put("roles", List.of("PROFILE", "OWNER"));
        } catch (UnauthorizedAccessException e){
            claims.put("roles", List.of("PROFILE"));
        }

        // JWT 논스 토큰 생성
        String token = tokenProvider.createToken(profileId.toString(), TokenType.NONCE, claims);

        NonceToken nonceToken = NonceToken.builder()
                .token(token)
                .clientId(app.getClientId())
                .profileId(profileId)
                .redirectUri(app.getRedirectUri())
                .expiresAt(LocalDateTime.now().plus(Duration.ofMillis(nonceTokenValidity)))
                .build();

        return NonceTokenResponse.of(nonceTokenRepository.save(nonceToken));
    }

    @Transactional
    public void validateNonceToken(NonceTokenValidationRequest request) {
        NonceToken nonceToken = nonceTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new AppException(AppErrorCode.NONCE_NOT_FOUND));

        // JWT 토큰 검증
        if (!tokenProvider.validateToken(nonceToken.getToken()) || 
            !tokenProvider.validateTokenType(nonceToken.getToken(), TokenType.NONCE)) {
            throw new AppException(AppErrorCode.INVALID_NONCE_TOKEN);
        }

        // app client secret 검증
        App app = appRepository.findByClientId((String)tokenProvider.getClaims(nonceToken.getToken()).get("clientId"))
            .orElseThrow(() -> new AppException(AppErrorCode.APP_NOT_FOUND));
        if (!app.getClientSecret().equals(request.getClientSecret())) {
            throw new AppException(AppErrorCode.INVALID_CLIENT_SECRET);
        }

        if (nonceToken.isUsed()) {
            throw new AppException(AppErrorCode.NONCE_TOKEN_ALREADY_USED);
        }

        if (nonceToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(AppErrorCode.NONCE_TOKEN_EXPIRED);
        }

        nonceToken.markAsUsed();
    }

    @Transactional
    public void cleanupExpiredTokens() {
        List<NonceToken> expiredTokens = nonceTokenRepository.findAllByExpiresAtBefore(LocalDateTime.now());
        nonceTokenRepository.deleteAll(expiredTokens);
    }
}
