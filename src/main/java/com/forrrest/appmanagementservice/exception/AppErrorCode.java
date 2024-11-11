package com.forrrest.appmanagementservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppErrorCode {
    // 404 NOT_FOUND
    APP_NOT_FOUND(HttpStatus.NOT_FOUND, "AP001", "앱을 찾을 수 없습니다."),
    CONNECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "APP-002", "앱 연결을 찾을 수 없습니다."),
    NONCE_NOT_FOUND(HttpStatus.NOT_FOUND, "AP009", "Nonce 토큰을 찾을 수 없습니다."),

    // 400 BAD_REQUEST
    INVALID_APP_STATUS(HttpStatus.BAD_REQUEST, "AP003", "유효하지 않은 앱 상태입니다."),
    INVALID_CLIENT_SECRET(HttpStatus.BAD_REQUEST, "AP004", "잘못된 client secret입니다."),
    INVALID_REDIRECT_URI(HttpStatus.BAD_REQUEST, "AP005", "잘못된 redirect URI입니다."),
    CONNECTION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AP006", "이미 연결된 앱입니다."),
    INVALID_NONCE_TOKEN(HttpStatus.BAD_REQUEST, "AP010", "유효하지 않은 Nonce 토큰입니다."),

    // 403 FORBIDDEN
    NOT_APP_OWNER(HttpStatus.FORBIDDEN, "AP007", "앱의 소유자가 아닙니다."),
    NOT_CONNECTION_OWNER(HttpStatus.FORBIDDEN, "AP008", "앱 연결의 소유자가 아닙니다."),

    // 401 UNAUTHORIZED
    NONCE_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AP011", "만료된 Nonce 토큰입니다."),
    
    // 409 CONFLICT
    NONCE_TOKEN_ALREADY_USED(HttpStatus.CONFLICT, "AP012", "이미 사용된 Nonce 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
