package com.kigen.car_reservation_api.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.payment.ETransactionType;

public interface TransactionTypeDAO extends JpaRepository<ETransactionType, Integer>, JpaSpecificationExecutor<ETransactionType> {
    
    Boolean existsByName(String name);
}
