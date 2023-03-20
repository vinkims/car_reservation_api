package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.EFuelType;

public interface FuelTypeDAO extends JpaRepository<EFuelType, Integer>, JpaSpecificationExecutor<EFuelType> {
    
    Boolean existsByName(String name);
}
