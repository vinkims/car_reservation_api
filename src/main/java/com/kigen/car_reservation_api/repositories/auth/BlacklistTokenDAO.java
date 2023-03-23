package com.kigen.car_reservation_api.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.security.EBlacklistToken;

public interface BlacklistTokenDAO extends JpaRepository<EBlacklistToken, Integer> {
    
    Boolean existsByTokenHash(Integer tokenHash);
}
