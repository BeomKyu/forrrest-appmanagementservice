package com.forrrest.appmanagementservice.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forrrest.appmanagementservice.entity.AppConnection;

@Repository
public interface AppConnectionRepository extends JpaRepository<AppConnection, Long> {
    // 내가 연결한 앱 목록 조회
    Page<AppConnection> findByProfileId(Long profileId, Pageable pageable);
    
    // 앱의 연결 목록 조회
    Page<AppConnection> findByAppId(Long appId, Pageable pageable);
    
    // 앱과 프로필로 연결 조회 (중복 연결 방지)
    Optional<AppConnection> findByAppIdAndProfileId(Long appId, Long profileId);
    
    // 앱과 프로필로 연결 여부 확인
    boolean existsByAppIdAndProfileId(Long appId, Long profileId);
}