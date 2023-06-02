package com.kigen.car_reservation_api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.user.EContactType;

public interface ContactTypeDAO extends JpaRepository<EContactType, Integer>, JpaSpecificationExecutor<EContactType> {
    
    Boolean existsByName(String name);
}
