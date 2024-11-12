package com.forrrest.appmanagementservice.dto.response;

import java.time.LocalDateTime;

import com.forrrest.appmanagementservice.entity.AppConnection;
import com.forrrest.appmanagementservice.enums.AppConnectionStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppConnectionResponse {
    private Long id;
    private Long appId;
    private Long profileId;
    private String profileName;
    private AppConnectionStatus status;
    private String redirectUri;
    private LocalDateTime lastAccessedAt;
    private LocalDateTime createdAt;

    @Builder
    public AppConnectionResponse(AppConnection connection) {
        this.id = connection.getId();
        this.appId = connection.getApp().getId();
        this.profileId = connection.getProfileId();
        this.profileName = connection.getProfileName();
        this.status = connection.getStatus();
        this.redirectUri = connection.getApp().getRedirectUri();
        this.lastAccessedAt = connection.getLastAccessedAt();
        this.createdAt = connection.getCreatedAt();
    }

    public static AppConnectionResponse of(AppConnection connection) {
        return new AppConnectionResponse(connection);
    }
}