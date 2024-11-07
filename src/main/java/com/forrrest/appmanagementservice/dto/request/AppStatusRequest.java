package com.forrrest.appmanagementservice.dto.request;

import com.forrrest.appmanagementservice.enums.AppStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppStatusRequest {
    
    @NotNull(message = "상태는 필수입니다")
    private AppStatus status;

    @Builder
    public AppStatusRequest(AppStatus status) {
        this.status = status;
    }
}