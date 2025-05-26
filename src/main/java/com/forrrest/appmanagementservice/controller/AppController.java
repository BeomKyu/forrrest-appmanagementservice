package com.forrrest.appmanagementservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forrrest.appmanagementservice.dto.request.AppRequest;
import com.forrrest.appmanagementservice.dto.request.AppSearchRequest;
import com.forrrest.appmanagementservice.dto.request.AppStatusRequest;
import com.forrrest.appmanagementservice.dto.request.AppUpdateRequest;
import com.forrrest.appmanagementservice.dto.response.AppDetailResponse;
import com.forrrest.appmanagementservice.dto.response.AppResponse;
import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.service.AppService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
        @Valid @RequestBody AppRequest request) {
        return ResponseEntity.ok(appService.createApp(request));
    }

    @Operation(summary = "앱 상세 조회", description = "생성자만 사용 가능")
    @GetMapping("/{appId}")
    public ResponseEntity<AppDetailResponse> getApp(@PathVariable Long appId) {
        return ResponseEntity.ok(appService.getAppDetail(appId));
    }

    @Operation(summary = "내 앱 목록 조회")
    @GetMapping
    public ResponseEntity<Page<AppResponse>> getMyApps(
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(appService.getMyApps(pageable));
    }

    @Operation(summary = "앱 정보 수정")
    @PatchMapping("/{appId}")
    public ResponseEntity<AppResponse> updateApp(
        @PathVariable Long appId,
        @Valid @RequestBody AppUpdateRequest request) {
        return ResponseEntity.ok(appService.updateApp(appId, request));
    }

    @Operation(summary = "앱 삭제")
    @DeleteMapping("/{appId}")
    public ResponseEntity<Void> deleteApp(@PathVariable Long appId) {
        appService.deleteApp(appId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "앱 상태 변경")
    @PatchMapping("/{appId}/status")
    public ResponseEntity<Void> updateStatus(
        @PathVariable Long appId,
        @Valid @RequestBody AppStatusRequest request) {
        appService.updateStatus(appId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "앱 검색")
    @GetMapping("/search")
    public ResponseEntity<Page<AppResponse>> searchApps(
        @ModelAttribute AppSearchRequest request,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(appService.searchApps(request, pageable));
    }

    @Operation(summary = "전체 앱 목록 조회")
    @GetMapping("/all")
    public ResponseEntity<Page<AppResponse>> getAllApps(
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(appService.getAllApps(pageable));
    }

    @Operation(summary = "앱 카테고리 목록 조회")
    @GetMapping("/categories")
    public ResponseEntity<List<AppCategory>> getCategories() {
        return ResponseEntity.ok(Arrays.asList(AppCategory.values()));
    }
}