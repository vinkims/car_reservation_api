package com.kigen.car_reservation_api.services.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.VehicleDTO;
import com.kigen.car_reservation_api.models.EVehicle;

public interface IVehicle {
    
    EVehicle create(VehicleDTO vehicleDTO);

    Optional<EVehicle> getById(Integer vehicleId);

    EVehicle getById(Integer vehicleId, Boolean handleException);

    Page<EVehicle> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EVehicle vehicle);

    EVehicle update(EVehicle vehicle, VehicleDTO vehicleDTO);
}
