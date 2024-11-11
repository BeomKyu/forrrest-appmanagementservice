package com.forrrest.appmanagementservice.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.forrrest.appmanagementservice.entity.AppConnection;
import com.forrrest.appmanagementservice.enums.AppConnectionStatus;

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
    
    // 새로 추가할 메서드들
    @Query("SELECT ac FROM AppConnection ac WHERE ac.app.id = :appId AND ac.status = :status")
    Page<AppConnection> findByAppIdAndStatus(
        @Param("appId") Long appId,
        @Param("status") AppConnectionStatus status,
        Pageable pageable
    );
    
    @Query("SELECT COUNT(ac) FROM AppConnection ac WHERE ac.app.id = :appId")
    long countByAppId(@Param("appId") Long appId);
    
    @Query("SELECT ac FROM AppConnection ac WHERE ac.profileId = :profileId AND ac.status = :status")
    Page<AppConnection> findByProfileIdAndStatus(
        @Param("profileId") Long profileId,
        @Param("status") AppConnectionStatus status,
        Pageable pageable
    );
    
    @Query("SELECT ac FROM AppConnection ac WHERE ac.app.id = :appId AND ac.lastAccessedAt < :date")
    List<AppConnection> findInactiveConnections(
        @Param("appId") Long appId,
        @Param("date") LocalDateTime date
    );
}