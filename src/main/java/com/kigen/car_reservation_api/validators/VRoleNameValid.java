package com.kigen.car_reservation_api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsRoleNameValid;
import com.kigen.car_reservation_api.services.role.IRole;

@Component
public class VRoleNameValid implements ConstraintValidator<IsRoleNameValid, String> {

    @Autowired
    private IRole sRole;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? true : !sRole.checkExistsByName(value);
    }
    
}
