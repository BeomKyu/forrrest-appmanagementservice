package com.forrrest.appmanagementservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forrrest.appmanagementservice.dto.request.AppRequest;
import com.forrrest.appmanagementservice.dto.response.AppResponse;
import com.forrrest.appmanagementservice.enums.AppStatus;
import com.forrrest.appmanagementservice.service.AppService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "App", description = "앱 관리 API")
@RestController
@RequestMapping("/apps")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token")
public class AppController {

    private final AppService appService;

    @Operation(summary = "앱 생성")
    @PostMapping
    public ResponseEntity<AppResponse> createApp(
        @RequestBody AppRequest request,
        @AuthenticationPrincipal Long profileId) {
        return ResponseEntity.ok(appService.createApp(request, profileId));
    }

    @Operation(summary = "앱 조회")
    @GetMapping("/{id}")
    public ResponseEntity<AppResponse> getApp(@PathVariable Long id) {
        return ResponseEntity.ok(appService.getApp(id));
    }

    @Operation(summary = "프로필의 앱 목록 조회")
    @GetMapping("/profile")
    public ResponseEntity<List<AppResponse>> getMyApps(
        @AuthenticationPrincipal Long profileId) {
        return ResponseEntity.ok(appService.getAppsByProfileId(profileId));
    }

    @Operation(summary = "앱 상태 변경")
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateAppStatus(
        @PathVariable Long id,
        @RequestParam AppStatus status) {
        appService.updateAppStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "앱 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApp(@PathVariable Long id) {
        appService.deleteApp(id);
        return ResponseEntity.ok().build();
    }
}