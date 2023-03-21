package com.kigen.car_reservation_api.controllers.status;

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
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.status.IStatus;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Statuses", description = "Status Endpoints")
public class CStatus {
    
    @Autowired
    private IStatus sStatus;

    @PostMapping(path = "/status", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createStatus(@Valid @RequestBody StatusDTO statusDTO) throws URISyntaxException {

        EStatus status = sStatus.create(statusDTO);

        return ResponseEntity
            .created(new URI("/status/" + status.getId()))
            .body(new SuccessResponse(201, "successfully created status", new StatusDTO(status)));
    }

    @GetMapping(path = "/status", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getStatusesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>();

        Page<EStatus> statuses = sStatus.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched statuses", statuses, 
                StatusDTO.class, EStatus.class));
    }

    @GetMapping(path = "/status/{statusId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getStatus(@PathVariable Integer statusId) {

        Optional<EStatus> statusOpt = sStatus.getById(statusId);
        if (!statusOpt.isPresent()) {
            throw new NotFoundException("status with specified id not found", "statusId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched status", new StatusDTO(statusOpt.get())));
    }

    @PatchMapping(path = "/status/{statusId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateStatus(@PathVariable Integer statusId, @Valid @RequestBody StatusDTO statusDTO) {

        Optional<EStatus> statusOpt = sStatus.getById(statusId);
        if (!statusOpt.isPresent()) {
            throw new NotFoundException("status with specified id not found", "statusId");
        }

        EStatus status = sStatus.update(statusOpt.get(), statusDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updates status", new StatusDTO(status)));
    }
}
