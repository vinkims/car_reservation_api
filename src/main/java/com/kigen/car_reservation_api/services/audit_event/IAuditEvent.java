package com.kigen.car_reservation_api.services.audit_event;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;

public interface IAuditEvent {
    
    EAuditEvent create(AuditEventDTO auditEventDTO);

    Optional<EAuditEvent> getById(Integer eventId);

    EAuditEvent getById(Integer eventId, Boolean handleException);

    Page<EAuditEvent> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EAuditEvent auditEvent);

    EAuditEvent update(EAuditEvent auditEvent, AuditEventDTO auditEventDTO);
}
