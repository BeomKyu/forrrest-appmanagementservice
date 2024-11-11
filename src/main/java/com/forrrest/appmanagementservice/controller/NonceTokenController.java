package com.forrrest.appmanagementservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forrrest.appmanagementservice.dto.request.NonceTokenRequest;
import com.forrrest.appmanagementservice.dto.request.NonceTokenValidationRequest;
import com.forrrest.appmanagementservice.dto.response.NonceTokenResponse;
import com.forrrest.appmanagementservice.service.NonceTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "NonceToken", description = "논스 토큰 API")
@RestController
@RequestMapping("/nonce-tokens")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token")
public class NonceTokenController {

    private final NonceTokenService nonceTokenService;

    @Operation(summary = "논스 토큰 발급")
    @PostMapping
    public ResponseEntity<NonceTokenResponse> createNonceToken(
        @Valid @RequestBody NonceTokenRequest request) {
        return ResponseEntity.ok(nonceTokenService.createNonceToken(request));
    }

    @Operation(summary = "논스 토큰 검증")
    @PostMapping("/validate")
    public ResponseEntity<Void> validateNonceToken(
        @Valid @RequestBody NonceTokenValidationRequest request) {
        nonceTokenService.validateNonceToken(request);
        return ResponseEntity.ok().build();
    }
}
