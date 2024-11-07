package com.forrrest.appmanagementservice.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.forrrest.appmanagementservice.dto.request.AppUpdateRequest;
import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.enums.AppStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "apps")
public class App extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String publicKey;

    @Column(nullable = false)
    private String redirectUri;

    @Column(nullable = false)
    private Long profileId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus status = AppStatus.ACTIVE;

    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
    private List<AppConnection> connections = new ArrayList<>();

    @Builder
    public App(String name, String description, String publicKey,
        String redirectUri, Long profileId, AppCategory category) {
        this.name = name;
        this.description = description;
        this.publicKey = publicKey;
        this.redirectUri = redirectUri;
        this.profileId = profileId;
        this.category = category;
        this.clientId = UUID.randomUUID().toString();
    }

    public void update(AppUpdateRequest request) {
        if (request.getName() != null) {
            this.name = request.getName();
        }
        if (request.getDescription() != null) {
            this.description = request.getDescription();
        }
        if (request.getPublicKey() != null) {
            this.publicKey = request.getPublicKey();
        }
        if (request.getCategory() != null) {
            this.category = request.getCategory();
        }
    }

    public void updateStatus(AppStatus status) {
        this.status = status;
    }
}