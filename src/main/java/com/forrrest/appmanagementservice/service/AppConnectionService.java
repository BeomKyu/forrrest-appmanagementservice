package com.forrrest.appmanagementservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forrrest.appmanagementservice.dto.request.AppConnectionRequest;
import com.forrrest.appmanagementservice.dto.response.AppConnectionResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.entity.AppConnection;
import com.forrrest.appmanagementservice.exception.CustomException;
import com.forrrest.appmanagementservice.exception.ErrorCode;
import com.forrrest.appmanagementservice.repository.AppConnectionRepository;
import com.forrrest.appmanagementservice.repository.AppRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppConnectionService {

    private final AppConnectionRepository appConnectionRepository;
    private final AppRepository appRepository;

    @Transactional
    public AppConnectionResponse connect(AppConnectionRequest request, Long profileId) {
        App app = appRepository.findById(request.getAppId())
            .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND));

        if (appConnectionRepository.existsByAppIdAndProfileId(app.getId(), profileId)) {
            throw new CustomException(ErrorCode.APP_DUPLICATION, "Already connected with this app");
        }

        AppConnection connection = AppConnection.builder()
            .app(app)
            .profileId(profileId)
            .profileName(request.getProfileName())
            .build();

        return AppConnectionResponse.from(appConnectionRepository.save(connection));
    }

    public List<AppConnectionResponse> getConnectionsByProfileId(Long profileId) {
        return appConnectionRepository.findByProfileIdWithApp(profileId).stream()
            .map(AppConnectionResponse::from)
            .collect(Collectors.toList());
    }

    public List<AppConnectionResponse> getConnectionsByAppId(Long appId) {
        if (!appRepository.existsById(appId)) {
            throw new CustomException(ErrorCode.APP_NOT_FOUND);
        }
        return appConnectionRepository.findByAppId(appId).stream()
            .map(AppConnectionResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateLastAccess(Long appId, Long profileId) {
        AppConnection connection = appConnectionRepository.findByAppIdAndProfileId(appId, profileId)
            .orElseThrow(() -> new CustomException(ErrorCode.APP_CONNECTION_NOT_FOUND));
        connection.updateLastAccessedAt();
    }

    @Transactional
    public void disconnect(Long appId, Long profileId) {
        AppConnection connection = appConnectionRepository.findByAppIdAndProfileId(appId, profileId)
            .orElseThrow(() -> new CustomException(ErrorCode.APP_CONNECTION_NOT_FOUND));
        appConnectionRepository.delete(connection);
    }
}