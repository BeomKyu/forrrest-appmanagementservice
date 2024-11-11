package com.forrrest.appmanagementservice.service;

import com.forrrest.appmanagementservice.dto.request.AppRequest;
import com.forrrest.appmanagementservice.dto.request.AppSearchRequest;
import com.forrrest.appmanagementservice.dto.request.AppUpdateRequest;
import com.forrrest.appmanagementservice.dto.response.AppDetailResponse;
import com.forrrest.appmanagementservice.dto.response.AppResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppStatus;
import com.forrrest.appmanagementservice.exception.AppNotFoundException;
import com.forrrest.appmanagementservice.repository.AppRepository;
import com.forrrest.appmanagementservice.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppService {

    private final AppRepository appRepository;

    @Transactional
    public AppResponse createApp(AppRequest request) {
        Long profileId = SecurityUtils.getCurrentProfileId();
        
        App app = App.builder()
                .name(request.getName())
                .description(request.getDescription())
                .redirectUri(request.getRedirectUri())
                .profileId(profileId)
                .category(request.getCategory())
                .build();

        return AppResponse.of(appRepository.save(app));
    }

    public AppDetailResponse getAppDetail(Long appId) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException("앱을 찾을 수 없습니다."));
        
        SecurityUtils.validateAppOwnership(app);
        
        return AppDetailResponse.of(app);
    }

    public Page<AppResponse> getMyApps(Pageable pageable) {
        Long profileId = SecurityUtils.getCurrentProfileId();
        return appRepository.findByProfileId(profileId, pageable)
                .map(AppResponse::of);
    }

    @Transactional
    public AppResponse updateApp(Long appId, AppUpdateRequest request) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException("앱을 찾을 수 없습니다."));
        
        SecurityUtils.validateAppOwnership(app);
        
        app.update(request);
        return AppResponse.of(app);
    }

    @Transactional
    public void deleteApp(Long appId) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException("앱을 찾을 수 없습니다."));
        
        SecurityUtils.validateAppOwnership(app);
        
        appRepository.delete(app);
    }

    @Transactional
    public void updateStatus(Long appId, AppStatus status) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException("앱을 찾을 수 없습니다."));
        
        SecurityUtils.validateAppOwnership(app);
        
        app.updateStatus(status);
    }

    public Page<AppResponse> searchApps(AppSearchRequest request, Pageable pageable) {
        if (request.getName() != null && !request.getName().isEmpty()) {
            return appRepository.findByNameContaining(request.getName(), pageable)
                    .map(AppResponse::of);
        }
        
        if (request.getCategory() != null) {
            if (request.getStatus() != null) {
                return appRepository.findByCategoryAndStatus(request.getCategory(), request.getStatus(), pageable)
                        .map(AppResponse::of);
            }
            return appRepository.findByCategory(request.getCategory(), pageable)
                    .map(AppResponse::of);
        }
        
        if (request.getStatus() != null) {
            return appRepository.findByStatus(request.getStatus(), pageable)
                    .map(AppResponse::of);
        }
        
        // 검색 조건이 없는 경우 전체 목록 반환
        return appRepository.findAll(pageable)
                .map(AppResponse::of);
    }

    public Page<AppResponse> getAllApps(Pageable pageable) {
        return appRepository.findAll(pageable)
                .map(AppResponse::of);
    }
}
