package com.forrrest.appmanagementservice.exception;

public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(String message) {
        super(message);
    }
}