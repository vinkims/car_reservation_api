package com.kigen.car_reservation_api.services.audit_event;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDataDTO;
import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventData;

public interface IAuditEventData {
    
    EAuditEventData create(AuditEventDataDTO eventDataDTO, EAuditEvent auditEvent);

    void save(EAuditEventData eventData);
}
