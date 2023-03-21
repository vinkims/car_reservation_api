package com.kigen.car_reservation_api.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.vehicle.EFuelType;

public interface FuelTypeDAO extends JpaRepository<EFuelType, Integer>, JpaSpecificationExecutor<EFuelType> {
    
    Boolean existsByName(String name);
}
