package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.ELocation;

public interface LocationDAO extends JpaRepository<ELocation, Integer>, JpaSpecificationExecutor<ELocation> {
    
}
