package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.EVehicle;

public interface VehicleDAO extends JpaRepository<EVehicle, Integer>, JpaSpecificationExecutor<EVehicle> {
    
}
