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

import com.kigen.car_reservation_api.dtos.contact.ContactTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.user.EContactType;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.contact.IContactType;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Contact Types", description = "Contact Type Endpoints")
public class CContactType {

    @Autowired
    private IContactType sContactType;
    
    @PostMapping(path = "/contact/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createRole(@Valid @RequestBody ContactTypeDTO contactTypeDTO) 
            throws URISyntaxException {

        EContactType contactType = sContactType.create(contactTypeDTO);

        return ResponseEntity
            .created(new URI("/contact/type/" + contactType.getId()))
            .body(new SuccessResponse(201, "successfully created contact type", new ContactTypeDTO(contactType)));
    }

    @GetMapping(path = "/contact/type", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getRolesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>();

        Page<EContactType> contactTypes = sContactType.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched roles", contactTypes, 
                ContactTypeDTO.class, EContactType.class));
    }
    
    @GetMapping(path = "/contact/type/{contactTypeId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getRole(@PathVariable Integer contactTypeId) {

        Optional<EContactType> contactTypeOpt = sContactType.getById(contactTypeId);
        if (!contactTypeOpt.isPresent()) {
            throw new NotFoundException("contact type with specified id not found", "contactTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched contact type", new ContactTypeDTO(contactTypeOpt.get())));
    }

    @PatchMapping(path = "/contact/type/{contactTypeId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateRole(@PathVariable Integer contactTypeId, @Valid @RequestBody ContactTypeDTO contactTypeDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Optional<EContactType> contactTypeOpt = sContactType.getById(contactTypeId);
        if (!contactTypeOpt.isPresent()) {
            throw new NotFoundException("contact type with specified id not found", "contactTypeId");
        }

        EContactType contactType = sContactType.update(contactTypeOpt.get(), contactTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated contact type", new ContactTypeDTO(contactType)));
    }
}
