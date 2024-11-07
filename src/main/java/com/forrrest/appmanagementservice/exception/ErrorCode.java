package com.forrrest.appmanagementservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "Invalid Method"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "Invalid Type Value"),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "Unauthorized"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "Invalid Token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "Expired Token"),

    // App Information
    APP_NOT_FOUND(HttpStatus.NOT_FOUND, "AP001", "App Not Found"),
    APP_ALREADY_EXISTS(HttpStatus.CONFLICT, "AP002", "App Client ID is Duplicated"),
    INVALID_APP_STATUS(HttpStatus.BAD_REQUEST, "AP003", "App Status is Invalid"),
    
    // App Connection
    CONNECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "AP004", "Connection Not Found"),
    CONNECTION_ALREADY_EXISTS(HttpStatus.CONFLICT, "AP005", "Connection Already Exists"),
    NOT_CONNECTION_OWNER(HttpStatus.FORBIDDEN, "AP006", "Not Connection Owner"),
    APP_NOT_ACTIVE(HttpStatus.BAD_REQUEST, "AP007", "App is not active"),
    
    // App Authorization
    NOT_APP_OWNER(HttpStatus.FORBIDDEN, "AP008", "Not App Owner"),
    INVALID_PUBLIC_KEY(HttpStatus.BAD_REQUEST, "AP009", "Invalid Public Key Format");

    private final HttpStatus status;
    private final String code;
    private final String message;
} 