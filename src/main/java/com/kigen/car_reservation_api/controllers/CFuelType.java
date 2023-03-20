package com.kigen.car_reservation_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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
import com.kigen.car_reservation_api.dtos.vehicle.FuelTypeDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.EFuelType;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.vehicle.IFuelType;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Fuel Types", description = "Fuel Types Endpoints")
public class CFuelType {
    
    @Autowired
    private IFuelType sFuelType;

    @PostMapping(path = "/fuel/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createFuelType(@Valid @RequestBody FuelTypeDTO fuelTypeDTO) throws URISyntaxException {

        EFuelType fuelType = sFuelType.create(fuelTypeDTO);

        return ResponseEntity
            .created(new URI("/transmission/type/" + fuelType.getId()))
            .body(new SuccessResponse(201, "successfully created fuel type", new FuelTypeDTO(fuelType)));
    }

    @GetMapping(path = "/fuel/type", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getFuelTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>();

        Page<EFuelType> fuelTypes = sFuelType.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched fuel types", fuelTypes, 
                FuelTypeDTO.class, EFuelType.class));
    }

    @GetMapping(path = "/fuel/type/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getFuelType(@PathVariable Integer id) {

        Optional<EFuelType> fuelTypeOpt = sFuelType.getById(id);
        if (!fuelTypeOpt.isPresent()) {
            throw new NotFoundException("fuel type with specified id not found", "fuelTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched fuel type", new FuelTypeDTO(fuelTypeOpt.get())));
    }

    @PatchMapping(path = "/fuel/type/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateFuelType(@PathVariable Integer id, @Valid @RequestBody FuelTypeDTO fuelTypeDTO) {

        Optional<EFuelType> fuelTypeOpt = sFuelType.getById(id);
        if (!fuelTypeOpt.isPresent()) {
            throw new NotFoundException("fuel type with specified id not found", "fuelTypeId");
        }

        EFuelType fuelType = sFuelType.update(fuelTypeOpt.get(), fuelTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated fuel type", new FuelTypeDTO(fuelType)));
    }
}
