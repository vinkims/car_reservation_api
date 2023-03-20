package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.ERole;

public interface RoleDAO extends JpaRepository<ERole, Integer>, JpaSpecificationExecutor<ERole> {
    
    Boolean existsByName(String name);
}
