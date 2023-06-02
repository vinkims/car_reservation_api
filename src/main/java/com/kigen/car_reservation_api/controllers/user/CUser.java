package com.kigen.car_reservation_api.controllers.user;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.user.IUser;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Users", description = "User Endpoints")
public class CUser {
    
    @Autowired
    private IUser sUser;

    @PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {

        EUser user = sUser.create(userDTO);

        return ResponseEntity
            .created(new URI("/user/" + user.getId()))
            .body(new SuccessResponse(201, "successfully created user", new UserDTO(user)));
    }

    @GetMapping(path = "/user", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getUsersList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(
            Arrays.asList("firstName", "middleName", "lastName", "createdOn", "age", "role.id", "status.id",
            "civilIdentities.civilIdentityValue")
        );

        Page<EUser> users = sUser.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched users", users, 
                UserDTO.class, EUser.class));
    }

    @GetMapping(path = "/user/{userValue}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getUser(@PathVariable String userValue) {

        Optional<EUser> userOpt = sUser.getByIdOrContactValue(userValue);
        if (!userOpt.isPresent()) {
            throw new NotFoundException("user with specified id or contact value not found", "userValue");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched user", new UserDTO(userOpt.get())));
    }

    @PatchMapping(path = "/user/{userValue}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateUser(@PathVariable String userValue, @Valid @RequestBody UserDTO userDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
            SecurityException {

        Optional<EUser> userOpt = sUser.getByIdOrContactValue(userValue);
        if (!userOpt.isPresent()) {
            throw new NotFoundException("user with specified id or contact value not found", "userValue");
        }

        EUser user = sUser.update(userOpt.get(), userDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successully updated user", new UserDTO(user)));
    }
}
