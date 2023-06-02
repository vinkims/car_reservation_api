package com.kigen.car_reservation_api.controllers.vehicle;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.VehicleDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.vehicle.EVehicle;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.vehicle.IVehicle;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Vehicles", description = "Vehicle Endpoints")
public class CVehicle {
    
    @Autowired
    private IVehicle sVehicle;

    @PostMapping(path = "/vehicle", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createVehicle(@RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {

        EVehicle vehicle = sVehicle.create(vehicleDTO);

        return ResponseEntity
            .created(new URI("/vehicle/" + vehicle.getId()))
            .body(new SuccessResponse(201, "successfully created vehicle", new VehicleDTO(vehicle)));
    }

    @GetMapping(path = "/vehicle", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getVehicleList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList(
            "createdOn", "modifiedOn", "lastActiveOn", "registrationNumber", "color", "engineCapacity",
            "bookingAmount", "transmissionType.id", "fuelType.id", "status.id", "model.vehiclemake", "model.vehicleModel"
        ));

        Page<EVehicle> vehicles = sVehicle.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched vehicles", vehicles, 
                VehicleDTO.class, EVehicle.class));
    }

    @GetMapping(path = "/vehicle/{vehicleId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getVehicle(@PathVariable Integer vehicleId) {

        Optional<EVehicle> vehicleOpt = sVehicle.getById(vehicleId);
        if (!vehicleOpt.isPresent()) {
            throw new NotFoundException("vehicle with specified id not found", "vehicleId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched vehicle", new VehicleDTO(vehicleOpt.get())));
    }

    @PatchMapping(path = "/vehicle/{vehicleId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateVehicle(@PathVariable Integer vehicleId, @RequestBody VehicleDTO vehicleDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
            SecurityException {

        Optional<EVehicle> vehicleOpt = sVehicle.getById(vehicleId);
        if (!vehicleOpt.isPresent()) {
            throw new NotFoundException("vehicle with specified id not found", "vehicleId");
        }

        EVehicle vehicle = sVehicle.update(vehicleOpt.get(), vehicleDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated vehicle", new VehicleDTO(vehicle)));
    }
}
