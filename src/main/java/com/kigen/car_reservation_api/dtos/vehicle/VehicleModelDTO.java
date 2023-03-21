package com.kigen.car_reservation_api.dtos.vehicle;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.vehicle.EVehicleModel;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class VehicleModelDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    private String vehicleMake;

    private String vehicleModel;

    public VehicleModelDTO(EVehicleModel model) {
        setCreatedOn(model.getCreatedOn());
        setId(model.getId());
        setModifiedOn(model.getModifiedOn());
        setVehicleMake(model.getVehicleMake());
        setVehicleModel(model.getVehicleModel());
    }
}
