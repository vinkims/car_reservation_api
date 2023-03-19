package com.kigen.car_reservation_api.services.vehicle;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.vehicle.VehicleModelDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.EVehicleModel;
import com.kigen.car_reservation_api.repositories.VehicleModelDAO;

@Service
public class SVehicleModel implements IVehicleModel {

    @Autowired
    private VehicleModelDAO vehicleModelDAO;

    @Override
    public EVehicleModel create(VehicleModelDTO vehicleModelDTO) {
        
        EVehicleModel model = new EVehicleModel();
        model.setCreatedOn(LocalDateTime.now());
        model.setVehicleMake(vehicleModelDTO.getVehicleMake());
        model.setVehicleModel(vehicleModelDTO.getVehicleModel());

        save(model);
        return model;
    }

    @Override
    public Optional<EVehicleModel> getById(Integer vehicleModelId) {
        return vehicleModelDAO.findById(vehicleModelId);
    }

    @Override
    public EVehicleModel getById(Integer vehicleModelId, Boolean handleException) {
        Optional<EVehicleModel> vehicleModel = getById(vehicleModelId);
        if (!vehicleModel.isPresent() && handleException) {
            throw new InvalidInputException("vehicle model with specified id not found", "vehicleModelId");
        }
        return vehicleModel.get();
    }

    @Override
    public Optional<EVehicleModel> getByMakeAndModel(String make, String model) {
        return vehicleModelDAO.findByMakeAndModel(make, model);
    }

    @Override
    public void save(EVehicleModel vehicleModel) {
        vehicleModelDAO.save(vehicleModel);
    }
    
}
