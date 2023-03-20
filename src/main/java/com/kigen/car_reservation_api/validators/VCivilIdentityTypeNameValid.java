package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsCivilIdentityTypeNameValid;
import com.kigen.car_reservation_api.services.civilIdentity.ICivilIdentityType;

@Component
public class VCivilIdentityTypeNameValid implements ConstraintValidator<IsCivilIdentityTypeNameValid, String> {

    @Autowired
    private ICivilIdentityType sCivilIdentityType;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sCivilIdentityType.checkExistsByName(value);
    }

    
    
}
