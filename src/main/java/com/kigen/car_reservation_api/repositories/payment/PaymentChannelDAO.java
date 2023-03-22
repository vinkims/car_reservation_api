package com.kigen.car_reservation_api.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.payment.EPaymentChannel;

public interface PaymentChannelDAO extends JpaRepository<EPaymentChannel, Integer>, JpaSpecificationExecutor<EPaymentChannel> {
    
    Boolean existsByName(String name);
}
