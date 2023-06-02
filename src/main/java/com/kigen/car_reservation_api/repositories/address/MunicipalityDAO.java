package com.kigen.car_reservation_api.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.address.EMunicipality;

public interface MunicipalityDAO extends JpaRepository<EMunicipality, Integer>, JpaSpecificationExecutor<EMunicipality> {
    
}
