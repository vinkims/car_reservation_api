package com.kigen.car_reservation_api.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.vehicle.ETransmissionType;

public interface TransmissionTypeDAO extends JpaRepository<ETransmissionType, Integer>, JpaSpecificationExecutor<ETransmissionType> {
    
    Boolean existsByName(String name);
}
