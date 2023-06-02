package com.kigen.car_reservation_api.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.annotations.IsContactValid;
import com.kigen.car_reservation_api.dtos.user.UserContactDTO;
import com.kigen.car_reservation_api.services.user.IUserContact;

@Component
public class VContactValid implements ConstraintValidator<IsContactValid, UserContactDTO> {

    @Value(value = "${default.value.contact.email-type-id}")
    private Integer emailContactTypeId;

    @Value(value = "${default.value.contact.mobile-type-id}")
    private Integer mobileContactTypeId;

    @Autowired
    private IUserContact sUserContact;

    private String defaultMessage;

    @Override
    public void initialize(final IsContactValid isContactValidAnnotation) {
        defaultMessage = isContactValidAnnotation.message();
    }

    @Override
    public boolean isValid(UserContactDTO userContactDTO, ConstraintValidatorContext context) {
        Boolean isContactValid;
        String message = "";
        Pattern pattern = Pattern.compile("\\w*");

        context.disableDefaultConstraintViolation();

        if (userContactDTO.getContactTypeId() == emailContactTypeId) {
            pattern = Pattern.compile("^(?<a>\\w*)(?<b>[\\.-]\\w*){0,3}@(?<c>\\w+)(?<d>\\.\\w{2,}){1,3}$");
            message = "Invalid email address; value provided must have standard email format";
        } else if (userContactDTO.getContactTypeId() == mobileContactTypeId) {
            pattern = Pattern.compile("^[0-9]{12}$");
            message = "Invalid mobile number; value provided must be 12 digits including country code";
        }

        Matcher matcher = pattern.matcher(userContactDTO.getContactValue());
        isContactValid = matcher.find();
        message = isContactValid ? defaultMessage : message;

        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return isContactValid && !sUserContact.checkExistsByContactValue(userContactDTO.getContactValue().trim());
    }
    
}
