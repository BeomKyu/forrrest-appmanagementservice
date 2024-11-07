package com.forrrest.appmanagementservice.dto.request;

import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.enums.AppStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppSearchRequest {
    private String name;
    private AppCategory category;
    private AppStatus status;

    @Builder
    public AppSearchRequest(String name, AppCategory category, AppStatus status) {
        this.name = name;
        this.category = category;
        this.status = status;
    }
}