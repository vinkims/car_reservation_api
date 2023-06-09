package com.kigen.car_reservation_api.repositories.vehicle;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.vehicle.EVehicleModel;

public interface VehicleModelDAO extends JpaRepository<EVehicleModel, Integer> {
    
    Optional<EVehicleModel> findByVehicleMakeAndVehicleModel(String vehicleMake, String vehicleModel);
}
