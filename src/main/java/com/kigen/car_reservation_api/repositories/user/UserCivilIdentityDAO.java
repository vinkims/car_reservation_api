package com.kigen.car_reservation_api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.user.EUserCivilIdentity;

public interface UserCivilIdentityDAO extends JpaRepository<EUserCivilIdentity, String> {
    
    Boolean existsByCivilIdentityValue(String civilIdentityValue);
}
