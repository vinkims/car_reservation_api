package com.kigen.car_reservation_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
@EqualsAndHashCode(callSuper = false)
public class InvalidInputException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String exception_msg;

    private String invalidField;

    public InvalidInputException(String exception) {
        super(exception);
        this.exception_msg = exception;
    }

    public InvalidInputException(String exception, String field) {
        super(exception);
        this.exception_msg = exception;
        this.invalidField = field;
    }
}
