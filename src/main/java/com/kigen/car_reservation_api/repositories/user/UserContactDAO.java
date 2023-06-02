package com.kigen.car_reservation_api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.user.EUserContact;

public interface UserContactDAO extends JpaRepository<EUserContact, String> {
    
    Boolean existsByContactValue(String contactValue);
}
