package com.forrrest.appmanagementservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NonceTokenRequest {
    @NotNull(message = "연결 ID는 필수입니다")
    private Long connectionId;
    
    @Builder
    public NonceTokenRequest(Long connectionId) {
        this.connectionId = connectionId;
    }
}
