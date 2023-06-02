package com.kigen.car_reservation_api.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.address.ECountry;

public interface CountryDAO extends JpaRepository<ECountry, Integer>, JpaSpecificationExecutor<ECountry> {
    
}
