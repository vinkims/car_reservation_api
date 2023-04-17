package com.kigen.car_reservation_api.repositories.audit_event;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventData;

public interface AuditEventDataDAO extends JpaRepository<EAuditEventData, EAuditEvent> {
    
}
