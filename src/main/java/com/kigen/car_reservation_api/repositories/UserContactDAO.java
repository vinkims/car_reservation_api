package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.EUserContact;

public interface UserContactDAO extends JpaRepository<EUserContact, String> {
    
    Boolean existsByContactValue(String contactValue);
}
