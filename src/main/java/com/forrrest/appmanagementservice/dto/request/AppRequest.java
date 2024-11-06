package com.forrrest.appmanagementservice.dto.request;

import com.forrrest.appmanagementservice.enums.AppCategory;

import lombok.Getter;

@Getter
public class AppRequest {
    private String name;
    private String description;
    private String publicKey;
    private String redirectUri;
    private AppCategory category;
}