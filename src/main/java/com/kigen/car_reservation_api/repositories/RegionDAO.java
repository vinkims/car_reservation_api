package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.ERegion;

public interface RegionDAO extends JpaRepository<ERegion, Integer>, JpaSpecificationExecutor<ERegion> {
    
}
