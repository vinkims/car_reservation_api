package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsFuelTypeNameValid;
import com.kigen.car_reservation_api.services.vehicle.IFuelType;

@Component
public class VFuelTypeNameValid implements ConstraintValidator<IsFuelTypeNameValid, String> {

    @Autowired
    private IFuelType sFuelType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sFuelType.checkExistsByName(value);
    }
    
}
