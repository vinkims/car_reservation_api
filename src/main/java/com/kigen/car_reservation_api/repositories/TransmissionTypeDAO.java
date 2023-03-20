package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.ETransmissionType;

public interface TransmissionTypeDAO extends JpaRepository<ETransmissionType, Integer>, JpaSpecificationExecutor<ETransmissionType> {
    
    Boolean existsByName(String name);
}
