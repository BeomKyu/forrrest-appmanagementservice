package com.forrrest.appmanagementservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<ErrorResponse> handleAppException(AppException e) {
        log.error("AppException: {}", e.getMessage());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
