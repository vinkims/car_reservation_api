package com.kigen.car_reservation_api.services.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.FuelTypeDTO;
import com.kigen.car_reservation_api.models.vehicle.EFuelType;

public interface IFuelType {
    
    Boolean checkExistsByName(String name);

    EFuelType create(FuelTypeDTO fuelTypeDTO);

    Optional<EFuelType> getById(Integer fuelTypeId);

    EFuelType getById(Integer fuelTypeId, Boolean handleException);

    Page<EFuelType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EFuelType fuelType);

    EFuelType update(EFuelType fuelType, FuelTypeDTO fuelTypeDTO);
}
