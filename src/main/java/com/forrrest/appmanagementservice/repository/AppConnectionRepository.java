package com.forrrest.appmanagementservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forrrest.appmanagementservice.entity.AppConnection;

public interface AppConnectionRepository extends JpaRepository<AppConnection, Long> {
    List<AppConnection> findByProfileId(Long profileId);

    List<AppConnection> findByAppId(Long appId);

    Optional<AppConnection> findByAppIdAndProfileId(Long appId, Long profileId);

    @Query("SELECT ac FROM AppConnection ac JOIN FETCH ac.app WHERE ac.profileId = :profileId")
    List<AppConnection> findByProfileIdWithApp(@Param("profileId") Long profileId);

    boolean existsByAppIdAndProfileId(Long appId, Long profileId);
}