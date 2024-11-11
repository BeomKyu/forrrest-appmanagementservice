package com.forrrest.appmanagementservice.dto.request;

import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppRequest {
    
    @NotBlank(message = "앱 이름은 필수입니다")
    @Size(min = 2, max = 50, message = "앱 이름은 2-50자 사이여야 합니다")
    private String name;
    
    @Size(max = 1000, message = "설명은 1000자를 넘을 수 없습니다")
    private String description;
    
    @NotBlank(message = "리다이렉트 URI는 필수입니다")
    private String redirectUri;
    
    @NotNull(message = "카테고리는 필수입니다")
    private AppCategory category;

    @Builder
    public AppRequest(String name, String description, String redirectUri, AppCategory category) {
        this.name = name;
        this.description = description;
        this.redirectUri = redirectUri;
        this.category = category;
    }

    public App toEntity(Long profileId) {
        return App.builder()
            .name(name)
            .description(description)
            .redirectUri(redirectUri)
            .profileId(profileId)
            .category(category)
            .build();
    }
}