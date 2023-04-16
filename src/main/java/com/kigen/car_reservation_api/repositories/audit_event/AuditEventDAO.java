package com.kigen.car_reservation_api.repositories.audit_event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;

public interface AuditEventDAO extends JpaRepository<EAuditEvent, Integer>, JpaSpecificationExecutor<EAuditEvent> {
    
}
