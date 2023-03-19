package com.kigen.car_reservation_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.EVehicleModel;

public interface VehicleModelDAO extends JpaRepository<EVehicleModel, Integer> {
    
    Optional<EVehicleModel> findByMakeAndModel(String make, String model);
}
