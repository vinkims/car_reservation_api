package com.kigen.car_reservation_api.services.audit_event;

import java.util.Optional;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventTypeDTO;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventType;

public interface IAuditEventType {
    
    EAuditEventType create(AuditEventTypeDTO auditEventTypeDTO);

    Optional<EAuditEventType> getById(Integer eventTypeId);

    EAuditEventType getById(Integer eventTypeId, Boolean handleException);

    Optional<EAuditEventType> getByName(String name);

    void save(EAuditEventType auditEventType);
}
