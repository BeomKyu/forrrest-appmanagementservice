package com.forrrest.appmanagementservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forrrest.appmanagementservice.dto.request.AppConnectionRequest;
import com.forrrest.appmanagementservice.dto.response.AppConnectionResponse;
import com.forrrest.appmanagementservice.service.AppConnectionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "App Connection", description = "앱 연결 관리 API")
@RestController
@RequestMapping("/connections")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token")
public class AppConnectionController {

    private final AppConnectionService appConnectionService;

    @Operation(summary = "앱 연결")
    @PostMapping
    public ResponseEntity<AppConnectionResponse> connect(
        @RequestBody AppConnectionRequest request,
        @AuthenticationPrincipal Long profileId) {
        return ResponseEntity.ok(appConnectionService.connect(request, profileId));
    }

    @Operation(summary = "프로필의 연결된 앱 목록 조회")
    @GetMapping("/profile")
    public ResponseEntity<List<AppConnectionResponse>> getMyConnections(
        @AuthenticationPrincipal Long profileId) {
        return ResponseEntity.ok(appConnectionService.getConnectionsByProfileId(profileId));
    }

    @Operation(summary = "앱의 연결된 프로필 목록 조회")
    @GetMapping("/app/{appId}")
    public ResponseEntity<List<AppConnectionResponse>> getAppConnections(
        @PathVariable Long appId) {
        return ResponseEntity.ok(appConnectionService.getConnectionsByAppId(appId));
    }

    @Operation(summary = "앱 연결 해제")
    @DeleteMapping("/{appId}")
    public ResponseEntity<Void> disconnect(
        @PathVariable Long appId,
        @AuthenticationPrincipal Long profileId) {
        appConnectionService.disconnect(appId, profileId);
        return ResponseEntity.ok().build();
    }
}