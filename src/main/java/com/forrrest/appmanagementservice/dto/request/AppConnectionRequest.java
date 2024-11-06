package com.forrrest.appmanagementservice.dto.request;

import lombok.Getter;

@Getter
public class AppConnectionRequest {
    private Long appId;
    private String profileName;
}