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
import com.kigen.car_reservation_api.dtos.transmissionType.TransmissionTypeDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.ETransmissionType;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.transmissionType.ITransmissionType;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Transmission Types", description = "Transmission Types Endpoints")
public class CTransmissionType {
    
    @Autowired
    private ITransmissionType sTransmissionType;

    @PostMapping(path = "/transmission/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createTransmissionType(@Valid @RequestBody TransmissionTypeDTO transmissionTypeDTO) 
            throws URISyntaxException {

        ETransmissionType transmissionType = sTransmissionType.create(transmissionTypeDTO);

        return ResponseEntity
            .created(new URI("/transmission/type/" + transmissionType.getId()))
            .body(new SuccessResponse(201, "successfully created transmission type", 
                new TransmissionTypeDTO(transmissionType)));
    }

    @GetMapping(path = "/transmission/type", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getTransmissionTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>();

        Page<ETransmissionType> transmissionTypes = sTransmissionType.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched transmission types", 
                transmissionTypes, TransmissionTypeDTO.class, ETransmissionType.class));
    }

    @GetMapping(path = "/transmission/type/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getTransmissionType(@PathVariable Integer id) {

        Optional<ETransmissionType> transmissionTypeOpt = sTransmissionType.getById(id);
        if (!transmissionTypeOpt.isPresent()) {
            throw new NotFoundException("transmission type with specified id not found", "transmissionTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched transmission type", 
                new TransmissionTypeDTO(transmissionTypeOpt.get())));
    }

    @PatchMapping(path = "/transmission/type/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateTransmissionType(@PathVariable Integer id, @Valid @RequestBody TransmissionTypeDTO transmissionTypeDTO) {

        Optional<ETransmissionType> transmissionTypeOpt = sTransmissionType.getById(id);
        if (!transmissionTypeOpt.isPresent()) {
            throw new NotFoundException("transmission type with specified id not found", "transmissionTypeId");
        }

        ETransmissionType transmissionType = sTransmissionType.update(transmissionTypeOpt.get(), transmissionTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated transmission type", new TransmissionTypeDTO(transmissionType)));
    }
}
