package com.kigen.car_reservation_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.ETransactionType;

public interface TransactionTypeDAO extends JpaRepository<ETransactionType, Integer>, JpaSpecificationExecutor<ETransactionType> {
    
    Boolean existsByName(String name);
}
