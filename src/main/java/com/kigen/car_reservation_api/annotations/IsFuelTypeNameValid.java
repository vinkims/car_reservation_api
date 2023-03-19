package com.kigen.car_reservation_api.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.kigen.car_reservation_api.validators.VFuelTypeNameValid;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Constraint(validatedBy = VFuelTypeNameValid.class)
public @interface IsFuelTypeNameValid {
    
    String message() default "Invalid fuel type name; provided name already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
