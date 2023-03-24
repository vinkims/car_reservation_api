package com.kigen.car_reservation_api.dtos.auth;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthDTO {
    
    @NotBlank
    private String userContact;

    @NotBlank
    private String password;
}
