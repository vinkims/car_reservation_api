package com.kigen.car_reservation_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.kigen.car_reservation_api.dtos.address.CountryDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.ECountry;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.address.ICountry;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Countries", description = "Countries Endpoints")
public class CCountry {
    
    @Autowired
    private ICountry sCountry;

    @PostMapping(path = "/country", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCountry(@RequestBody CountryDTO countryDTO) throws URISyntaxException {

        ECountry country = sCountry.create(countryDTO);

        return ResponseEntity
            .created(new URI("/country/" + country.getId()))
            .body(new SuccessResponse(201, "successfully created country", new CountryDTO(country)));
    }

    @GetMapping(path = "/country", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getCountriesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        if (pageDTO.getSortVal().equals("createdOn")) {
            pageDTO.setSortVal("id");
        }

        List<String> allowableFields = new ArrayList<>();

        Page<ECountry> countries = sCountry.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched countries", countries, 
                CountryDTO.class, ECountry.class));
    }

    @GetMapping(path = "/country/{countryId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCountry(@PathVariable Integer countryId) {

        Optional<ECountry> countryOpt = sCountry.getById(countryId);
        if (!countryOpt.isPresent()) {
            throw new NotFoundException("country with specified id not found", "countryId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched country", new CountryDTO(countryOpt.get())));
    }

    @PatchMapping(path = "/country/{countryId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCountry(@PathVariable Integer countryId, @RequestBody CountryDTO countryDTO) {

        Optional<ECountry> countryOpt = sCountry.getById(countryId);
        if (!countryOpt.isPresent()) {
            throw new NotFoundException("country with specified id not found", "countryId");
        }

        ECountry country = sCountry.update(countryOpt.get(), countryDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated country", new CountryDTO(country)));
    }
}
