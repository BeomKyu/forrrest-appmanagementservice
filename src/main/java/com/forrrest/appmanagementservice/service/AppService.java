package com.forrrest.appmanagementservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forrrest.appmanagementservice.dto.request.AppRequest;
import com.forrrest.appmanagementservice.dto.response.AppResponse;
import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppStatus;
import com.forrrest.appmanagementservice.exception.CustomException;
import com.forrrest.appmanagementservice.exception.ErrorCode;
import com.forrrest.appmanagementservice.repository.AppRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppService {

    private final AppRepository appRepository;

    @Transactional
    public AppResponse createApp(AppRequest request, Long profileId) {

        App app = App.builder()
                .name(request.getName())
                .description(request.getDescription())
                .publicKey(request.getPublicKey())
                .redirectUri(request.getRedirectUri())
                .category(request.getCategory())
                .profileId(profileId)
                .build();
        
        return AppResponse.from(appRepository.save(app));
    }

    public AppResponse getApp(Long id) {
        return AppResponse.from(appRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND)));
    }

    public AppResponse getAppByClientId(String clientId) {
        return AppResponse.from(appRepository.findByClientId(clientId)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND)));
    }

    public List<AppResponse> getAppsByProfileId(Long profileId) {
        return appRepository.findByProfileId(profileId).stream()
                .map(AppResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateAppStatus(Long id, AppStatus status) {
        App app = appRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.APP_NOT_FOUND));
        app.updateStatus(status);
    }

    @Transactional
    public void deleteApp(Long id) {
        if (!appRepository.existsById(id)) {
            throw new CustomException(ErrorCode.APP_NOT_FOUND);
        }
        appRepository.deleteById(id);
    }
}