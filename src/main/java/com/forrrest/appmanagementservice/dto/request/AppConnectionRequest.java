package com.forrrest.appmanagementservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppConnectionRequest {
    @NotNull(message = "앱 ID는 필수입니다")
    private Long appId;

    @Builder
    public AppConnectionRequest(Long appId) {
        this.appId = appId;
    }
}