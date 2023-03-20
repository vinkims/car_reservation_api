package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.kigen.car_reservation_api.annotations.IsTransactionTypeNameValid;
import com.kigen.car_reservation_api.services.payment.ITransactionType;

public class VTransactionTypeNameValid implements ConstraintValidator<IsTransactionTypeNameValid, String> {

    @Autowired
    private ITransactionType sTransactionType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sTransactionType.checkExistsByName(value);
    }
    
}
