package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsContactTypeNameValid;
import com.kigen.car_reservation_api.services.contact.IContactType;

@Component
public class VContactTypeNameValid implements ConstraintValidator<IsContactTypeNameValid, String> {

    @Autowired
    private IContactType sContactType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sContactType.checkExistsByName(value);
    }
    
}
