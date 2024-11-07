package com.forrrest.appmanagementservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.enums.AppStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String clientId;
    private String publicKey;
    private AppCategory category;
    private AppStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AppConnectionResponse> connections;

    @Builder
    public AppDetailResponse(App app) {
        this.id = app.getId();
        this.name = app.getName();
        this.description = app.getDescription();
        this.clientId = app.getClientId();
        this.publicKey = app.getPublicKey();
        this.category = app.getCategory();
        this.status = app.getStatus();
        this.createdAt = app.getCreatedAt();
        this.updatedAt = app.getUpdatedAt();
        this.connections = app.getConnections().stream()
            .map(AppConnectionResponse::of)
            .collect(Collectors.toList());
    }

    public static AppDetailResponse of(App app) {
        return new AppDetailResponse(app);
    }
}