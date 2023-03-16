package com.kigen.car_reservation_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String exceptionMsg;

    private String invalidField;

    public InvalidInputException(String exception) {
        super(exception);
        this.exceptionMsg = exception;
    }

    public InvalidInputException(String exception, String field) {
        super(exception);
        this.exceptionMsg = exception;
        this.invalidField = field;
    }
}
