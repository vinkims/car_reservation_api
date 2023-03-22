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

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.role.RoleDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.user.ERole;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.role.IRole;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Roles", description = "Role Endpoints")
public class CRole {
    
    @Autowired
    private IRole sRole;

    @PostMapping(path = "/role", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createRole(@Valid @RequestBody RoleDTO roleDTO) throws URISyntaxException {

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
