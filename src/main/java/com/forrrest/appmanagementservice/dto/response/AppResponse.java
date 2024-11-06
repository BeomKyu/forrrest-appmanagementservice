package com.forrrest.appmanagementservice.dto.response;

import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.enums.AppStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppResponse {
    private Long id;
    private String name;
    private String description;
    private String clientId;
    private String publicKey;
    private String redirectUri;
    private AppCategory category;
    private AppStatus status;
    private Long profileId;

    public static AppResponse from(App app) {
        return AppResponse.builder()
            .id(app.getId())
            .name(app.getName())
            .description(app.getDescription())
            .clientId(app.getClientId())
            .publicKey(app.getPublicKey())
            .redirectUri(app.getRedirectUri())
            .category(app.getCategory())
            .status(app.getStatus())
            .profileId(app.getProfileId())
            .build();
    }
}