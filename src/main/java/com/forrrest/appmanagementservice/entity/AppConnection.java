package com.forrrest.appmanagementservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "app_connections")
public class AppConnection extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", nullable = false)
    private App app;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false)
    private String profileName;

    private LocalDateTime lastAccessedAt;

    @Builder
    public AppConnection(App app, Long profileId, String profileName) {
        this.app = app;
        this.profileId = profileId;
        this.profileName = profileName;
        this.lastAccessedAt = LocalDateTime.now();
    }

    public void updateLastAccessedAt() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}