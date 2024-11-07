package com.forrrest.appmanagementservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forrrest.appmanagementservice.dto.request.AppRequest;
import com.forrrest.appmanagementservice.dto.request.AppSearchRequest;
import com.forrrest.appmanagementservice.dto.request.AppUpdateRequest;
import com.forrrest.appmanagementservice.dto.response.AppDetailResponse;
import com.forrrest.appmanagementservice.dto.response.AppResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppStatus;
import com.forrrest.appmanagementservice.exception.CustomException;
import com.forrrest.appmanagementservice.exception.ErrorCode;
import com.forrrest.appmanagementservice.repository.AppRepository;
import com.forrrest.common.security.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppService {
    private final AppRepository appRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public AppResponse createApp(AppRequest request) {
        // 앱 이름 중복 체크
        if (appRepository.existsByName(request.getName())) {
            throw new CustomException(ErrorCode.APP_ALREADY_EXISTS);
        }

        App app = App.builder()
            .name(request.getName())
            .description(request.getDescription())
            .publicKey(request.getPublicKey())
            .profileId(securityUtil.getCurrentProfileId())
            .category(request.getCategory())
            .build();

        return AppResponse.of(appRepository.save(app));
    }

    public AppDetailResponse getAppDetail(Long id) {
        App app = findAppById(id);
        validateAppOwner(app);
        return AppDetailResponse.of(app);
    }

    public Page<AppResponse> getMyApps(Pageable pageable) {
        return appRepository.findByProfileId(securityUtil.getCurrentProfileId(), pageable)
            .map(AppResponse::of);
    }

    @Transactional
    public AppResponse updateApp(Long id, AppUpdateRequest request) {
        App app = findAppById(id);
        validateAppOwner(app);

        // 이름 변경 시 중복 체크
        if (request.getName() != null && !app.getName().equals(request.getName()) 
            && appRepository.existsByName(request.getName())) {
            throw new CustomException(ErrorCode.APP_ALREADY_EXISTS);
        }

        app.update(request);
        return AppResponse.of(app);
    }

    @Transactional
    public void deleteApp(Long id) {
        App app = findAppById(id);
        validateAppOwner(app);
        appRepository.delete(app);
    }

    @Transactional
    public void updateStatus(Long id, AppStatus status) {
        App app = findAppById(id);
        validateAppOwner(app);
        app.updateStatus(status);
    }

    public Page<AppResponse> searchApps(AppSearchRequest request, Pageable pageable) {
        // 검색 조건에 따른 쿼리 실행
        // TODO: QueryDSL로 동적 쿼리 구현 필요
        if (request.getName() != null) {
            return appRepository.findByNameContaining(request.getName(), pageable)
                .map(AppResponse::of);
        }
        if (request.getCategory() != null) {
            return appRepository.findByCategory(request.getCategory(), pageable)
                .map(AppResponse::of);
        }
        if (request.getStatus() != null) {
            return appRepository.findByStatus(request.getStatus(), pageable)
                .map(AppResponse::of);
        }
        return appRepository.findAll(pageable).map(AppResponse::of);
    }

    public Page<AppResponse> getAllApps(Pageable pageable) {
        return appRepository.findAll(pageable).map(AppResponse::of);
    }

    // 유틸리티 메서드
    private App findAppById(Long id) {
        return appRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND));
    }

    private void validateAppOwner(App app) {
        if (!app.getProfileId().equals(securityUtil.getCurrentProfileId())) {
            throw new CustomException(ErrorCode.NOT_APP_OWNER);
        }
    }
}