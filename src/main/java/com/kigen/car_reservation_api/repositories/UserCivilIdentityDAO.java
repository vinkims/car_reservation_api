package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.EUserCivilIdentity;

public interface UserCivilIdentityDAO extends JpaRepository<EUserCivilIdentity, String> {
    
    Boolean existsByCivilIdentityValue(String civilIdentityValue);
}
