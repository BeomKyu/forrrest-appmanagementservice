package com.forrrest.appmanagementservice.service;

import com.forrrest.appmanagementservice.dto.request.AppConnectionRequest;
import com.forrrest.appmanagementservice.dto.response.AppConnectionResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.entity.AppConnection;
import com.forrrest.appmanagementservice.enums.AppConnectionStatus;
import com.forrrest.appmanagementservice.enums.AppStatus;
import com.forrrest.appmanagementservice.exception.AppNotFoundException;
import com.forrrest.appmanagementservice.exception.UnauthorizedAccessException;
import com.forrrest.appmanagementservice.repository.AppConnectionRepository;
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
public class AppConnectionService {

    private final AppRepository appRepository;
    private final AppConnectionRepository appConnectionRepository;

    @Transactional
    public AppConnectionResponse connect(AppConnectionRequest request) {
        App app = appRepository.findById(request.getAppId())
                .orElseThrow(() -> new AppNotFoundException("앱을 찾을 수 없습니다."));

        if (app.getStatus() != AppStatus.ACTIVE) {
            throw new IllegalStateException("현재 설치할 수 없는 앱입니다.");
        }

        Long profileId = SecurityUtils.getCurrentProfileId();
        String profileName = SecurityUtils.getCurrentProfileName();

        if (appConnectionRepository.existsByAppIdAndProfileId(app.getId(), profileId)) {
            throw new IllegalStateException("이미 설치된 앱입니다.");
        }

        AppConnection connection = AppConnection.builder()
                .app(app)
                .profileId(profileId)
                .profileName(profileName)
                .status(AppConnectionStatus.CONNECTED)
                .build();

        return AppConnectionResponse.of(appConnectionRepository.save(connection));
    }

    @Transactional
    public AppConnectionResponse executeApp(Long connectionId) {
        AppConnection connection = appConnectionRepository.findById(connectionId)
                .orElseThrow(() -> new IllegalArgumentException("설치된 앱을 찾을 수 없습니다."));

        validateConnectionOwnership(connection);

        if (connection.getApp().getStatus() != AppStatus.ACTIVE) {
            throw new IllegalStateException("현재 실행할 수 없는 앱입니다.");
        }

        if (connection.getStatus() != AppConnectionStatus.CONNECTED) {
            throw new IllegalStateException("앱을 실행할 수 없는 상태입니다.");
        }

        connection.updateLastAccessedAt();
        return AppConnectionResponse.of(connection);
    }

    @Transactional
    public void disconnect(Long connectionId) {
        AppConnection connection = appConnectionRepository.findById(connectionId)
                .orElseThrow(() -> new IllegalArgumentException("연결을 찾을 수 없습니다."));

        validateConnectionOwnership(connection);
        
        appConnectionRepository.delete(connection);
    }

    public Page<AppConnectionResponse> getMyConnections(Pageable pageable) {
        Long profileId = SecurityUtils.getCurrentProfileId();
        return appConnectionRepository.findByProfileId(profileId, pageable)
                .map(AppConnectionResponse::of);
    }

    public Page<AppConnectionResponse> getConnectionsByApp(Long appId, Pageable pageable) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new AppNotFoundException("앱을 찾을 수 없습니다."));

        SecurityUtils.validateAppOwnership(app);

        return appConnectionRepository.findByAppId(appId, pageable)
                .map(AppConnectionResponse::of);
    }

    private void validateConnectionOwnership(AppConnection connection) {
        Long currentProfileId = SecurityUtils.getCurrentProfileId();
        if (!connection.getProfileId().equals(currentProfileId)) {
            throw new UnauthorizedAccessException("해당 연결에 대한 접근 권한이 없습니다.");
        }
    }
}
