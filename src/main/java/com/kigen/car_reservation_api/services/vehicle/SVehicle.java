package com.kigen.car_reservation_api.services.vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.VehicleDTO;
import com.kigen.car_reservation_api.dtos.vehicle.VehicleModelDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.EFuelType;
import com.kigen.car_reservation_api.models.EStatus;
import com.kigen.car_reservation_api.models.ETransmissionType;
import com.kigen.car_reservation_api.models.EVehicle;
import com.kigen.car_reservation_api.models.EVehicleModel;
import com.kigen.car_reservation_api.repositories.VehicleDAO;
import com.kigen.car_reservation_api.services.status.IStatus;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SVehicle implements IVehicle {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IFuelType sFuelType;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransmissionType sTransmissionType;

    @Autowired
    private IVehicleModel sVehicleModel;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Override
    public EVehicle create(VehicleDTO vehicleDTO) {

        EVehicle vehicle = new EVehicle();
        vehicle.setBookingAmount(vehicleDTO.getBookingAmount());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setCreatedOn(LocalDateTime.now());
        vehicle.setEngineCapacity(vehicleDTO.getEngineCapacity());
        vehicle.setRegistrationNumber(vehicleDTO.getRegistrationNumber());
        setFuelType(vehicle, vehicleDTO.getFuelTypeId());
        setVehicleModel(vehicle, vehicleDTO);

        Integer statusId = vehicleDTO.getStatusId() == null ? activeStatusId : vehicleDTO.getStatusId();
        setStatus(vehicle, statusId);
        
        setTransmissionType(vehicle, vehicleDTO.getTransmissionTypeId());

        save(vehicle);
        return vehicle;
    }

    @Override
    public Optional<EVehicle> getById(Integer vehicleId) {
        return vehicleDAO.findById(vehicleId);
    }

    @Override
    public EVehicle getById(Integer vehicleId, Boolean handleException) {
        Optional<EVehicle> vehicle = getById(vehicleId);
        if (!vehicle.isPresent() && handleException) {
            throw new InvalidInputException("vehicle with specified id not found", "vehicleId");
        }
        return vehicle.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EVehicle> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EVehicle> specBuilder = new SpecBuilder<EVehicle>();

        specBuilder = (SpecBuilder<EVehicle>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<EVehicle> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return vehicleDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EVehicle vehicle) {
        vehicleDAO.save(vehicle);
    }

    public void setFuelType(EVehicle vehicle, Integer fuelTypeId) {
        if (fuelTypeId == null) { return; }

        EFuelType fuelType = sFuelType.getById(fuelTypeId, true);
        vehicle.setFuelType(fuelType);
    }

    public void setStatus(EVehicle vehicle, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        vehicle.setStatus(status);
    }

    public void setTransmissionType(EVehicle vehicle, Integer transmissionTypeId) {
        if (transmissionTypeId == null) { return; }

        ETransmissionType transmissionType = sTransmissionType.getById(transmissionTypeId, true);
        vehicle.setTransmissionType(transmissionType);
    }

    public void setVehicleModel(EVehicle vehicle, VehicleDTO vehicleDTO) {
        if (vehicleDTO.getVehicleMake() == null || vehicleDTO.getVehicleModel() == null) { return; }

        String make = vehicleDTO.getVehicleMake();
        String model = vehicleDTO.getVehicleModel();

        Optional<EVehicleModel> vehicleModelOpt = sVehicleModel.getByMakeAndModel(make, model);
        EVehicleModel vehicleModel;
        if (vehicleModelOpt.isPresent()) {
            vehicleModel = vehicleModelOpt.get();
        } else {
            // Create ne vehicle model record
            VehicleModelDTO vehicleModelDTO = new VehicleModelDTO();
            vehicleModelDTO.setVehicleMake(make);
            vehicleModelDTO.setVehicleModel(model);
            vehicleModel = sVehicleModel.create(vehicleModelDTO);
        }
        vehicle.setModel(vehicleModel);
    }

    @Override
    public EVehicle update(EVehicle vehicle, VehicleDTO vehicleDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
