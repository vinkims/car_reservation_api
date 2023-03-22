package com.kigen.car_reservation_api.repositories.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.payment.EPayment;

public interface PaymentDAO extends JpaRepository<EPayment, Integer>, JpaSpecificationExecutor<EPayment> {
    
    Optional<EPayment> findByExternalTransactionId(String externalTransactionId);
}
