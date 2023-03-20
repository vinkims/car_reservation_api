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

import com.kigen.car_reservation_api.dtos.address.LocationDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.ELocation;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.address.ILocation;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Locations", description = "Locations Endpoints")
public class CLocation {

    @Autowired
    private ILocation sLocation;
    
    @PostMapping(path = "/location", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createLocation(@RequestBody LocationDTO LocationDTO) throws URISyntaxException {

        ELocation location = sLocation.create(LocationDTO);

        return ResponseEntity
            .created(new URI("/location/" + location.getId()))
            .body(new SuccessResponse(201, "successfully created location", new LocationDTO(location)));
    }

    @GetMapping(path = "/location", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getLocationsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList("createdOn", "area.id", "area.name", "name"));

        Page<ELocation> locations = sLocation.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched locations", locations, 
                LocationDTO.class, ELocation.class));
    }

    @GetMapping(path = "/location/{locationId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getLocation(@PathVariable Integer locationId) {

        Optional<ELocation> locationOpt = sLocation.getById(locationId);
        if (!locationOpt.isPresent()) {
            throw new NotFoundException("location with specified id not found", "locationId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched location", new LocationDTO(locationOpt.get())));
    }

    @PatchMapping(path = "/location/{locationId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateLocation(@PathVariable Integer locationId, @RequestBody LocationDTO locationDTO) {

        Optional<ELocation> locationOpt = sLocation.getById(locationId);
        if (!locationOpt.isPresent()) {
            throw new NotFoundException("location with specified id not found", "locationId");
        }

        ELocation location = sLocation.update(locationOpt.get(), locationDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(2002, "successfully updated location", new LocationDTO(location)));
    }
}
