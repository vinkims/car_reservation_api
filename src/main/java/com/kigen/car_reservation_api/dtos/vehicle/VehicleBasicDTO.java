package com.kigen.car_reservation_api.dtos.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.vehicle.EVehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class VehicleBasicDTO {
    
    private Integer id;

    private String registrationNumber;

    private String vehicleMake;

    private String vehicleModel;

    private String color;

    @JsonIgnoreProperties(value = {"createdOn", "description"})
    private TransmissionTypeDTO transmissionType;

    @JsonIgnoreProperties(value = {"createdOn", "description"})
    private FuelTypeDTO fuelType;

    public VehicleBasicDTO(EVehicle vehicle) {
        setColor(vehicle.getColor());
        setId(vehicle.getId());
        setRegistrationNumber(vehicle.getRegistrationNumber());
        if (vehicle.getModel() != null) {
            setVehicleMake(vehicle.getModel().getVehicleMake());
            setVehicleModel(vehicle.getModel().getVehicleModel());
        }
        if (vehicle.getTransmissionType() != null) {
            setTransmissionType(new TransmissionTypeDTO(vehicle.getTransmissionType()));
        }
        if (vehicle.getFuelType() != null) {
            setFuelType(new FuelTypeDTO(vehicle.getFuelType()));
        }
    }
}
