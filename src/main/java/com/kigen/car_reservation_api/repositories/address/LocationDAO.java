package com.kigen.car_reservation_api.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.address.ELocation;

public interface LocationDAO extends JpaRepository<ELocation, Integer>, JpaSpecificationExecutor<ELocation> {
    
}
