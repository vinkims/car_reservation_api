package com.kigen.car_reservation_api.controllers.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.car_reservation_api.dtos.auth.AuthDTO;
import com.kigen.car_reservation_api.dtos.auth.SignoutDTO;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.auth.IAuth;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Auth", description = "User Authentication Endpoints")
public class CAuth {
    
    @Autowired
    private IAuth sAuth;

    @PostMapping(path = "/user/auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> authenticateUser(@Valid @RequestBody AuthDTO authDTO) {

        String token = sAuth.authenticateUser(authDTO);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully authenticated user", res));
    }

    @PostMapping(path = "/user/auth/sign-out", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> signoutUser(@Valid @RequestBody SignoutDTO signoutDTO) {

        Boolean isSignout = sAuth.signoutUser(signoutDTO);

        Map<String, Object> content = new HashMap<>();
        content.put("signout", isSignout);
        content.put("userId", signoutDTO.getUserId());
        content.put("timestamp", new Date().toString());

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successful signout", content));
    }
}
