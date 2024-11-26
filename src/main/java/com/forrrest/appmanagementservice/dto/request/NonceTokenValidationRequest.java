package com.forrrest.appmanagementservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NonceTokenValidationRequest {
    @NotBlank(message = "토큰은 필수입니다")
    private String token;

    @NotBlank(message = "app client secret 는 필수입니다.")
    private String clientSecret;

}
