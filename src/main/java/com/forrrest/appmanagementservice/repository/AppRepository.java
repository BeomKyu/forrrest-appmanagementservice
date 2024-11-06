package com.forrrest.appmanagementservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forrrest.appmanagementservice.entity.App;

public interface AppRepository extends JpaRepository<App, Long> {
    Optional<App> findByClientId(String clientId);

    List<App> findByProfileId(Long profileId);

    boolean existsByClientId(String clientId);

    @Query("SELECT a FROM App a LEFT JOIN FETCH a.connections WHERE a.id = :id")
    Optional<App> findByIdWithConnections(@Param("id") Long id);
}