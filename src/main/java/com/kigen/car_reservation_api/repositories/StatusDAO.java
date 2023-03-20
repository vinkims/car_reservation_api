package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.EStatus;

public interface StatusDAO extends JpaRepository<EStatus, Integer>, JpaSpecificationExecutor<EStatus> {
    
    Boolean existsByName(String name);
}
