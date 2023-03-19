package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.ECivilIdentityType;

public interface CivilIdentityTypeDAO extends JpaRepository<ECivilIdentityType, Integer>, 
        JpaSpecificationExecutor<ECivilIdentityType> {
    
    Boolean existsByName(String name);
}
