package com.forrrest.appmanagementservice.dto.request;

import com.forrrest.appmanagementservice.enums.AppCategory;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppUpdateRequest {
    
    @Size(min = 2, max = 50, message = "앱 이름은 2-50자 사이여야 합니다")
    private String name;
    
    @Size(max = 1000, message = "설명은 1000자를 넘을 수 없습니다")
    private String description;
    
    private String publicKey;
    
    private AppCategory category;

    @Builder
    public AppUpdateRequest(String name, String description, String publicKey, AppCategory category) {
        this.name = name;
        this.description = description;
        this.publicKey = publicKey;
        this.category = category;
    }
}