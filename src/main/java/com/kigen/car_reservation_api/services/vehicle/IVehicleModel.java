package com.kigen.car_reservation_api.services.vehicle;

import java.util.Optional;

import com.kigen.car_reservation_api.dtos.vehicle.VehicleModelDTO;
import com.kigen.car_reservation_api.models.EVehicleModel;

public interface IVehicleModel {
    
    EVehicleModel create(VehicleModelDTO vehicleModelDTO);

    Optional<EVehicleModel> getById(Integer vehicleModelId);

    EVehicleModel getById(Integer vehicleModelId, Boolean handleException);

    Optional<EVehicleModel> getByMakeAndModel(String make, String model);

    void save(EVehicleModel vehicleModel);
}
