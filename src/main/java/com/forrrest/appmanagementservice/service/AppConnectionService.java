package com.forrrest.appmanagementservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forrrest.appmanagementservice.dto.request.AppConnectionRequest;
import com.forrrest.appmanagementservice.dto.response.AppConnectionResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.entity.AppConnection;
import com.forrrest.appmanagementservice.enums.AppStatus;
import com.forrrest.appmanagementservice.exception.CustomException;
import com.forrrest.appmanagementservice.exception.ErrorCode;
import com.forrrest.appmanagementservice.repository.AppConnectionRepository;
import com.forrrest.appmanagementservice.repository.AppRepository;
import com.forrrest.common.security.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppConnectionService {

    private final AppConnectionRepository appConnectionRepository;
    private final AppRepository appRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public AppConnectionResponse connect(AppConnectionRequest request) {
        // 앱 존재 여부 확인
        App app = appRepository.findById(request.getAppId())
            .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND));

        // 앱 상태 확인
        if (app.getStatus() != AppStatus.ACTIVE) {
            throw new CustomException(ErrorCode.INVALID_APP_STATUS);
        }

        Long profileId = securityUtil.getCurrentProfileId();

        // 이미 연결되어 있는지 확인
        if (appConnectionRepository.existsByAppIdAndProfileId(app.getId(), profileId)) {
            throw new CustomException(ErrorCode.CONNECTION_ALREADY_EXISTS);
        }

        AppConnection connection = AppConnection.builder()
            .app(app)
            .profileId(profileId)
            .profileName("")
            .build();

        return AppConnectionResponse.of(appConnectionRepository.save(connection));
    }

    public Page<AppConnectionResponse> getConnectionsByApp(Long appId, Pageable pageable) {
        // 앱 존재 여부 확인
        App app = appRepository.findById(appId)
            .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND));
            
        // 앱 소유자 확인
        if (!app.getProfileId().equals(securityUtil.getCurrentProfileId())) {
            throw new CustomException(ErrorCode.NOT_APP_OWNER);
        }

        return appConnectionRepository.findByAppId(appId, pageable)
            .map(AppConnectionResponse::of);
    }

    @Transactional
    public void disconnect(Long connectionId) {
        AppConnection connection = findConnectionById(connectionId);
        validateConnectionOwner(connection);
        appConnectionRepository.delete(connection);
    }

    public Page<AppConnectionResponse> getMyConnectedApps(Pageable pageable) {
        return appConnectionRepository.findByProfileId(securityUtil.getCurrentProfileId(), pageable)
            .map(AppConnectionResponse::of);
    }

    public AppConnectionResponse getConnection(Long connectionId) {
        AppConnection connection = findConnectionById(connectionId);
        validateConnectionOwner(connection);
        return AppConnectionResponse.of(connection);
    }

    // 유틸리티 메서드
    private AppConnection findConnectionById(Long id) {
        return appConnectionRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.CONNECTION_NOT_FOUND));
    }

    private void validateConnectionOwner(AppConnection connection) {
        if (!connection.getProfileId().equals(securityUtil.getCurrentProfileId())) {
            throw new CustomException(ErrorCode.NOT_CONNECTION_OWNER);
        }
    }
}