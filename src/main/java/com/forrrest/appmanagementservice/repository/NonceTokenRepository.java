package com.forrrest.appmanagementservice.repository;

import com.forrrest.appmanagementservice.entity.NonceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface NonceTokenRepository extends JpaRepository<NonceToken, Long> {
    Optional<NonceToken> findByToken(String token);
    
    boolean existsByToken(String token);
    
    @Query("SELECT n FROM NonceToken n WHERE n.clientId = :clientId AND n.used = false AND n.expiresAt > :now")
    List<NonceToken> findValidTokensByClientId(
        @Param("clientId") String clientId,
        @Param("now") LocalDateTime now
    );
    
    @Query("SELECT n FROM NonceToken n WHERE n.expiresAt < :now OR n.used = true")
    List<NonceToken> findExpiredOrUsedTokens(@Param("now") LocalDateTime now);
    
    @Query("SELECT n FROM NonceToken n WHERE n.profileId = :profileId AND n.clientId = :clientId AND n.used = false")
    Optional<NonceToken> findActiveTokenByProfileAndClient(
        @Param("profileId") Long profileId,
        @Param("clientId") String clientId
    );

    List<NonceToken> findAllByExpiresAtBefore(LocalDateTime now);
}
