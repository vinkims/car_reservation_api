package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsCivilIdentityValid;
import com.kigen.car_reservation_api.services.user.IUserCivilIdentity;

@Component
public class VCivilIdentityValid implements ConstraintValidator<IsCivilIdentityValid, String> {

    @Autowired
    private IUserCivilIdentity sUserCivilIdentity;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sUserCivilIdentity.checkExistsByCivilIdentityValue(value);
    }
    
}
