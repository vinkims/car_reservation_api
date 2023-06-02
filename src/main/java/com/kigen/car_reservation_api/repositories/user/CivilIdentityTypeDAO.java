package com.kigen.car_reservation_api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.user.ECivilIdentityType;

public interface CivilIdentityTypeDAO extends JpaRepository<ECivilIdentityType, Integer>, 
        JpaSpecificationExecutor<ECivilIdentityType> {
    
    Boolean existsByName(String name);
}
