package com.kigen.car_reservation_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.kigen.car_reservation_api.models.EUser;

public interface UserDAO extends JpaRepository<EUser, Integer>, JpaSpecificationExecutor<EUser> {
    
    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN user_contacts uc ON uc.user_id = u.id "
            + "WHERE uc.contact_value = :contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByContactValue(String contactValue);

    @Query(
        value = "SELECT * FROM users u "
            + "LEFT JOIN user_contacts uc ON uc.user_id = u.id "
            + "WHERE u.id = :userId "
            + "OR uc.contact_value = :contactValue",
        nativeQuery = true
    )
    Optional<EUser> findByIdOrContactValue(Integer userId, String contactValue);
}
