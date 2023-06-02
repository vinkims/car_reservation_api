package com.kigen.car_reservation_api.repositories.audit_event;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kigen.car_reservation_api.models.audit_events.EAuditEventType;

public interface AuditEventTypeDAO extends JpaRepository<EAuditEventType, Integer>, JpaSpecificationExecutor<EAuditEventType> {
    
    Optional<EAuditEventType> findByName(String name);
}
