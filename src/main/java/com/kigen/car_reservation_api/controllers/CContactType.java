package com.kigen.car_reservation_api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.car_reservation_api.dtos.contact.ContactTypeDTO;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.contact.IContactType;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Contact Types", description = "Contact Types Endpoints")
public class CContactType {

    @Autowired
    private IContactType sContactType;
    
    @PostMapping(path = "/contact/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createRole(@Valid @RequestBody ContactTypeDTO roleDTO) throws URISyntaxException {

        ERole role = sRole.create(roleDTO);

        return ResponseEntity
            .created(new URI("/role/" + role.getId()))
            .body(new SuccessResponse(201, "successfully created role", new RoleDTO(role)));
    }

    @GetMapping(path = "/role", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getRolesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>();

        Page<ERole> roles = sRole.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched roles", roles, 
                RoleDTO.class, ERole.class));
    }
    
    @GetMapping(path = "/role/{roleId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getRole(@PathVariable Integer roleId) {

        Optional<ERole> roleOpt = sRole.getById(roleId);
        if (!roleOpt.isPresent()) {
            throw new NotFoundException("role with specified id not found", "roleId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched role", new RoleDTO(roleOpt.get())));
    }

    @PatchMapping(path = "/role/{roleId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateRole(@PathVariable Integer roleId, @Valid @RequestBody RoleDTO roleDTO) {

        Optional<ERole> roleOpt = sRole.getById(roleId);
        if (!roleOpt.isPresent()) {
            throw new NotFoundException("role with specified id not found", "roleId");
        }

        ERole role = sRole.update(roleOpt.get(), roleDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated role", new RoleDTO(role)));
    }
}
