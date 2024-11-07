package com.forrrest.appmanagementservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.forrrest.appmanagementservice.entity.App;
import com.forrrest.appmanagementservice.enums.AppCategory;
import com.forrrest.appmanagementservice.enums.AppStatus;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
    // 내 앱 목록 조회
    Page<App> findByProfileId(Long profileId, Pageable pageable);
    
    // 앱 이름으로 검색
    Page<App> findByNameContaining(String name, Pageable pageable);
    
    // 카테고리로 검색
    Page<App> findByCategory(AppCategory category, Pageable pageable);
    
    // 상태로 검색
    Page<App> findByStatus(AppStatus status, Pageable pageable);
    
    // 앱 이름 중복 체크
    boolean existsByName(String name);
    
    // clientId로 앱 조회
    Optional<App> findByClientId(String clientId);
}