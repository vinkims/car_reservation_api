package com.kigen.car_reservation_api.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.address.EArea;

public interface AreaDAO extends JpaRepository<EArea, Integer>, JpaSpecificationExecutor<EArea> {
    
}
