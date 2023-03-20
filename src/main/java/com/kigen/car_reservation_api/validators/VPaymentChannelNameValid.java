package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.kigen.car_reservation_api.annotations.IsPaymentChannelNameValid;
import com.kigen.car_reservation_api.services.payment.IPaymentChannel;

public class VPaymentChannelNameValid implements ConstraintValidator<IsPaymentChannelNameValid, String> {

    @Autowired
    private IPaymentChannel sPaymentChannel;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true ? !sPaymentChannel.checkExistsByName(value);
    }
    
}
