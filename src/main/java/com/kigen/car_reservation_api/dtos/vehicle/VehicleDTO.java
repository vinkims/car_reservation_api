package com.kigen.car_reservation_api.dtos.vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.models.EVehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class VehicleDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime lastActiveOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    private String registrationNumber;

    private String vehicleMake;

    private String vehicleModel;

    private String color;

    private Integer engineCapacity;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private TransmissionTypeDTO transmissionType;

    private Integer transmissionTypeId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private FuelTypeDTO fuelType;

    private Integer fuelTypeId;

    private BigDecimal bookingAmount;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private StatusDTO status;

    private Integer statusId;

    public VehicleDTO(EVehicle vehicle) {
        setColor(vehicle.getColor());
        setCreatedOn(vehicle.getCreatedOn());
        setEngineCapacity(vehicle.getEngineCapacity());
        setFuelType(new FuelTypeDTO(vehicle.getFuelType()));
        setId(vehicle.getId());
        setLastActiveOn(vehicle.getLastActiveOn());
        setModifiedOn(vehicle.getModifiedOn());
        setRegistrationNumber(vehicle.getRegistrationNumber());
        setStatus(new StatusDTO(vehicle.getStatus()));
        setTransmissionType(new TransmissionTypeDTO(vehicle.getTransmissionType()));
        if (vehicle.getModel() != null) {
            setVehicleMake(vehicle.getModel().getVehicleMake());
            setVehicleModel(vehicle.getModel().getVehicleModel());
        }
    }
}
