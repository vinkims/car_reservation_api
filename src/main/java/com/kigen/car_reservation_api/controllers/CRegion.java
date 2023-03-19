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

import com.kigen.car_reservation_api.dtos.address.RegionDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.ERegion;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.address.IRegion;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Regions", description = "Regions Endpoints")
public class CRegion {
    
    @Autowired
    private IRegion sRegion;

    @PostMapping(path = "/region", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createRegion(@RequestBody RegionDTO regionDTO) throws URISyntaxException {

        ERegion region = sRegion.create(regionDTO);

        return ResponseEntity
            .created(new URI("/region/" + region.getId()))
            .body(new SuccessResponse(201, "successfully created region", new RegionDTO(region)));
    }

    @GetMapping(path = "/region", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getRegionsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        if (pageDTO.getSortVal().equals("createdOn")) {
            pageDTO.setSortVal("id");
        }

        List<String> allowableFields = new ArrayList<>(Arrays.asList("country.id"));

        Page<ERegion> regions = sRegion.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched regions", regions, 
                RegionDTO.class, ERegion.class));
    }
    
    @GetMapping(path = "/region/{regionId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getRegion(@PathVariable Integer regionId) {

        Optional<ERegion> regionOpt = sRegion.getById(regionId);
        if (!regionOpt.isPresent()) {
            throw new NotFoundException("region with specified id not found", "regionId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched region", new RegionDTO(regionOpt.get())));
    }

    @PatchMapping(path = "/region/{regionId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateRegion(@PathVariable Integer regionId, @RequestBody RegionDTO regionDTO) {

        Optional<ERegion> regionOpt = sRegion.getById(regionId);
        if (!regionOpt.isPresent()) {
            throw new NotFoundException("region with specified id not found", "regionId");
        }

        ERegion region = sRegion.update(regionOpt.get(), regionDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated region", new RegionDTO(region)));
    }
}
