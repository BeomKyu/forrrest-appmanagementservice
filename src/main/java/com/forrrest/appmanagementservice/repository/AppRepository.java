package com.forrrest.appmanagementservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 새로 추가할 메서드들
    @Query("SELECT a FROM App a WHERE a.profileId = :profileId AND a.status = :status")
    Page<App> findByProfileIdAndStatus(
        @Param("profileId") Long profileId, 
        @Param("status") AppStatus status, 
        Pageable pageable
    );

    @Query("SELECT a FROM App a WHERE a.category = :category AND a.status = :status")
    Page<App> findByCategoryAndStatus(
        @Param("category") AppCategory category, 
        @Param("status") AppStatus status, 
        Pageable pageable
    );

    @Query("SELECT COUNT(a) > 0 FROM App a WHERE a.name = :name AND a.profileId = :profileId")
    boolean existsByNameAndProfileId(
        @Param("name") String name, 
        @Param("profileId") Long profileId
    );

    @Query("SELECT a FROM App a WHERE a.status = :status AND " +
           "(LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<App> searchByKeywordAndStatus(
        @Param("keyword") String keyword,
        @Param("status") AppStatus status,
        Pageable pageable
    );
}