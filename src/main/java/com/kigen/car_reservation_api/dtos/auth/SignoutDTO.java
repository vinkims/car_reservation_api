package com.kigen.car_reservation_api.dtos.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignoutDTO {
    
    @NotBlank
    private String token;

    @NotNull
    private Integer userId;
}
