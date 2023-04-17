package com.kigen.car_reservation_api.controllers.audit_event;

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

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.audit_event.IAuditEvent;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Audit Events", description = "System Audit Events ENdpoints")
public class CAuditEvent {
    
    @Autowired
    private IAuditEvent sAuditEvent;

    @PostMapping(path = "/audit_event", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createAuditEvent(@RequestBody AuditEventDTO auditEventDTO) 
            throws URISyntaxException {

        EAuditEvent auditEvent = sAuditEvent.create(auditEventDTO);

        return ResponseEntity
            .created(new URI("/audit_event/" + auditEvent.getId()))
            .body(new SuccessResponse(201, "successfully created audit event", new AuditEventDTO(auditEvent)));
    }

    @GetMapping(path = "/audit_event", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList(
            "createdOn", "timestamp", "eventType", "principal", "status_id"
        ));

        Page<EAuditEvent> auditEvents = sAuditEvent.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched audit events", auditEvents, 
                AuditEventDTO.class, EAuditEvent.class));
    }

    @GetMapping(path = "/audit_event/{eventId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getAuditEvent(@PathVariable Integer eventId) {

        Optional<EAuditEvent> auditEventOpt = sAuditEvent.getById(eventId);
        if (!auditEventOpt.isPresent()) {
            throw new NotFoundException("audit event with specified id not found", "auditEventId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched audit event", new AuditEventDTO(auditEventOpt.get())));
    }

    @PatchMapping(path = "/audit_event/{eventId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateAuditEvent(@PathVariable Integer eventId, @RequestBody AuditEventDTO auditEventDTO) {

        Optional<EAuditEvent> auditEventOpt = sAuditEvent.getById(eventId);
        if (!auditEventOpt.isPresent()) {
            throw new NotFoundException("audit event with specified id not found", "auditEventId");
        }

        EAuditEvent auditEvent = sAuditEvent.update(auditEventOpt.get(), auditEventDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated audit event", new AuditEventDTO(auditEvent)));
    }
}
