package com.kigen.car_reservation_api.controllers;

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

import com.kigen.car_reservation_api.dtos.address.MunicipalityDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.EMunicipality;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.address.IMunicipality;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Municipalities", description = "Municipalities Endpoints")
public class CMunicipality {
    
    @Autowired
    private IMunicipality sMunicipality;

    @PostMapping(path = "/municipality", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createMunicipality(@RequestBody MunicipalityDTO municipalityDTO) throws URISyntaxException {

        EMunicipality municipality = sMunicipality.create(municipalityDTO);

        return ResponseEntity
            .created(new URI("/municipality/" + municipality.getId()))
            .body(new SuccessResponse(201, "successfully created municipality", new MunicipalityDTO(municipality)));
    }

    @GetMapping(path = "/municipality", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getMunicipalitiesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        if (pageDTO.getSortVal().equals("createdOn")) {
            pageDTO.setSortVal("id");
        }

        List<String> allowableFields = new ArrayList<>(Arrays.asList("region.id"));

        Page<EMunicipality> municipalities = sMunicipality.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched municipalities", municipalities, 
                MunicipalityDTO.class, EMunicipality.class));
    }
    
    @GetMapping(path = "/municipality/{municipalityId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getMunicipality(@PathVariable Integer municipalityId) {

        Optional<EMunicipality> municipalityOpt = sMunicipality.getById(municipalityId);
        if (!municipalityOpt.isPresent()) {
            throw new NotFoundException("municipality with specified id not found", "municipalityId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched municipality", 
                new MunicipalityDTO(municipalityOpt.get())));
    }

    @PatchMapping(path = "/municipality/{municipalityId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateMunicipality(@PathVariable Integer municipalityId, @RequestBody MunicipalityDTO municipalityDTO) {

        Optional<EMunicipality> municipalityOpt = sMunicipality.getById(municipalityId);
        if (!municipalityOpt.isPresent()) {
            throw new NotFoundException("municipality with specified id not found", "municipalityId");
        }

        EMunicipality municipality = sMunicipality.update(municipalityOpt.get(), municipalityDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated municipality", new MunicipalityDTO(municipality)));
    }
}
