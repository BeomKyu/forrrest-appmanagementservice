package com.forrrest.appmanagementservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "AppConnection", description = "앱 연결 관리 API")
@RestController
@RequestMapping("/app-connections")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token")
public class AppConnectionController {

    private final AppConnectionService appConnectionService;

    @Operation(summary = "앱 연결")
    @PostMapping
    public ResponseEntity<AppConnectionResponse> connect(
        @Valid @RequestBody AppConnectionRequest request) {
        return ResponseEntity.ok(appConnectionService.connect(request));
    }

    @Operation(summary = "앱 실행")
    @PostMapping("/{connectionId}/execute")
    public ResponseEntity<AppConnectionResponse> executeApp(
        @PathVariable Long connectionId) {
        return ResponseEntity.ok(appConnectionService.executeApp(connectionId));
    }

    @Operation(summary = "앱 연결 해제")
    @DeleteMapping("/{connectionId}")
    public ResponseEntity<Void> disconnect(@PathVariable Long connectionId) {
        appConnectionService.disconnect(connectionId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "내가 프로필에 연결한 앱 목록 조회")
    @GetMapping
    public ResponseEntity<Page<AppConnectionResponse>> getMyConnections(
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(appConnectionService.getMyConnections(pageable));
    }

    @Operation(summary = "앱의 연결 목록 조회")
    @GetMapping("/by-app/{appId}")
    public ResponseEntity<Page<AppConnectionResponse>> getConnectionsByApp(
        @PathVariable Long appId,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(appConnectionService.getConnectionsByApp(appId, pageable));
    }
}