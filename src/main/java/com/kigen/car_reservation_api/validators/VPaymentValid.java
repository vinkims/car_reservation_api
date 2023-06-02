package com.kigen.car_reservation_api.validators;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsPaymentValid;
import com.kigen.car_reservation_api.models.payment.EPayment;
import com.kigen.car_reservation_api.services.payment.IPayment;

@Component
public class VPaymentValid implements ConstraintValidator<IsPaymentValid, String> {

    @Autowired
    private IPayment sPayment;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<EPayment> payment = sPayment.getByExternalTransactionId(value);
        return !payment.isPresent();
    }
    
}
