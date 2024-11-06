package com.forrrest.appmanagementservice.dto.response;

import java.time.LocalDateTime;

import com.forrrest.appmanagementservice.entity.AppConnection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppConnectionResponse {
    private Long id;
    private Long appId;
    private String appName;
    private Long profileId;
    private String profileName;
    private LocalDateTime lastAccessedAt;
    private LocalDateTime connectedAt;

    public static AppConnectionResponse from(AppConnection connection) {
        return AppConnectionResponse.builder()
            .id(connection.getId())
            .appId(connection.getApp().getId())
            .appName(connection.getApp().getName())
            .profileId(connection.getProfileId())
            .profileName(connection.getProfileName())
            .lastAccessedAt(connection.getLastAccessedAt())
            .connectedAt(connection.getCreatedAt())
            .build();
    }
}