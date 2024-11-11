package com.forrrest.appmanagementservice.dto.response;

import java.time.LocalDateTime;

import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.enums.AppStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppResponse {
    private Long id;
    private String name;
    private String description;
    private String clientId;
    private String redirectUri;
    private AppCategory category;
    private AppStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long connectionCount;

    @Builder
    public AppResponse(App app) {
        this.id = app.getId();
        this.name = app.getName();
        this.description = app.getDescription();
        this.clientId = app.getClientId();
        this.redirectUri = app.getRedirectUri();
        this.category = app.getCategory();
        this.status = app.getStatus();
        this.createdAt = app.getCreatedAt();
        this.updatedAt = app.getUpdatedAt();
        this.connectionCount = (long) app.getConnections().size();
    }

    public static AppResponse of(App app) {
        return new AppResponse(app);
    }
}