package com.kigen.car_reservation_api.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.vehicle.EVehicle;

public interface VehicleDAO extends JpaRepository<EVehicle, Integer>, JpaSpecificationExecutor<EVehicle> {
    
}
