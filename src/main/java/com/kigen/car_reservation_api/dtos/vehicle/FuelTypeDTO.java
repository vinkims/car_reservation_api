package com.kigen.car_reservation_api.dtos.vehicle;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsFuelTypeNameValid;
import com.kigen.car_reservation_api.models.EFuelType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class FuelTypeDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsFuelTypeNameValid
    private String name;

    private String description;

    public FuelTypeDTO(EFuelType fuelType) {
        setCreatedOn(fuelType.getCreatedOn());
        setDescription(fuelType.getDescription());
        setId(fuelType.getId());
        setName(fuelType.getName());
    }
}
