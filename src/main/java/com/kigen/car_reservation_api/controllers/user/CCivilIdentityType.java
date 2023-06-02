package com.kigen.car_reservation_api.controllers.user;

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

import com.kigen.car_reservation_api.dtos.civilIdentity.CivilIdentityTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.user.ECivilIdentityType;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.civilIdentity.ICivilIdentityType;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Civil Identity Types", description = "Civil Identity Type Endpoints")
public class CCivilIdentityType {
    
    @Autowired
    private ICivilIdentityType sCivilIdentityType;

    @PostMapping(path = "/civil/identity/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createCivilIdentityType(@Valid @RequestBody CivilIdentityTypeDTO civilIdentityTypeDTO) 
            throws URISyntaxException {

        ECivilIdentityType civilIdentityType = sCivilIdentityType.create(civilIdentityTypeDTO);

        return ResponseEntity
            .created(new URI("/civil/identity/type/" + civilIdentityType.getId()))
            .body(new SuccessResponse(201, "successfully created civil identity type", 
                new CivilIdentityTypeDTO(civilIdentityType)));
    }

    @GetMapping(path = "/civil/identity/type", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getCivilIdentityTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>();

        Page<ECivilIdentityType> civilIdentityTypes = sCivilIdentityType.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched civil identity types", 
                civilIdentityTypes, CivilIdentityTypeDTO.class, ECivilIdentityType.class));
    }

    @GetMapping(path = "/civil/identity/type/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getCivilIdentityType(@PathVariable Integer id) {

        Optional<ECivilIdentityType> civilIdentityTypeOpt = sCivilIdentityType.getById(id);
        if (!civilIdentityTypeOpt.isPresent()) {
            throw new NotFoundException("civil identity type with specified id not found", "civilIdentityTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched civil identity type", 
                new CivilIdentityTypeDTO(civilIdentityTypeOpt.get())));
    }

    @PatchMapping(path = "/civil/identity/type/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateCivilIdentityType(@PathVariable Integer id, @Valid @RequestBody CivilIdentityTypeDTO civilIdentityTypeDTO) {

        Optional<ECivilIdentityType> civilIdentityTypeOpt = sCivilIdentityType.getById(id);
        if (!civilIdentityTypeOpt.isPresent()) {
            throw new NotFoundException("civil identity type with specified id not found", "civilIdentityTypeId");
        }

        ECivilIdentityType civilIdentityType = sCivilIdentityType.update(civilIdentityTypeOpt.get(), civilIdentityTypeDTO);
        
        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated civil identity type", new CivilIdentityTypeDTO(civilIdentityType)));
    }
}
