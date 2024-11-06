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

    // User
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "U001", "Email is Duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U002", "User Not Found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U003", "Password is Invalid"),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "Unauthorized"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "Invalid Token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "Expired Token"),

    // Profile
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "Profile Not Found"),
    PROFILE_DUPLICATION(HttpStatus.CONFLICT, "P002", "Profile Name is Duplicated"),
    PROFILE_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, "P003", "Profile Token is Required"),
    PROFILE_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "P004", "Profile Token is Invalid"),
    PROFILE_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "P005", "Profile Token is Expired"),

    // App
    APP_NOT_FOUND(HttpStatus.NOT_FOUND, "AP001", "App Not Found"),
    APP_DUPLICATION(HttpStatus.CONFLICT, "AP002", "App Client ID is Duplicated"),
    APP_CONNECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "AP003", "App Connection Not Found"),
    APP_STATUS_INVALID(HttpStatus.BAD_REQUEST, "AP004", "App Status is Invalid");

    private final HttpStatus status;
    private final String code;
    private final String message;
} 