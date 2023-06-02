package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsStatusNameValid;
import com.kigen.car_reservation_api.services.status.IStatus;

@Component
public class VStatusNameValid implements ConstraintValidator<IsStatusNameValid, String> {

    @Autowired
    private IStatus sStatus;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sStatus.checkExistsByName(value);
    }
    
}
