package com.kigen.car_reservation_api.services.payment;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.payment.PaymentDTO;
import com.kigen.car_reservation_api.models.payment.EPayment;

public interface IPayment {
    
    EPayment create(PaymentDTO paymentDTO);

    Optional<EPayment> getByExternalTransactionId(String externalTransactionId);

    Optional<EPayment> getById(Integer paymentId);

    EPayment getById(Integer paymentId, Boolean handleException);

    Page<EPayment> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EPayment payment);

    EPayment update(EPayment payment, PaymentDTO paymentDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
