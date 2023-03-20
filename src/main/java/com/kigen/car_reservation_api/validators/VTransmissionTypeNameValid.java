package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsTransmissionTypeNameValid;
import com.kigen.car_reservation_api.services.vehicle.ITransmissionType;

@Component
public class VTransmissionTypeNameValid implements ConstraintValidator<IsTransmissionTypeNameValid, String> {

    @Autowired
    private ITransmissionType sTransmissionType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sTransmissionType.checkExistsByName(value);
    }
    
}
