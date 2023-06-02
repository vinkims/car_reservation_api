package com.kigen.car_reservation_api.services.audit_event;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventTypeDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventType;
import com.kigen.car_reservation_api.repositories.audit_event.AuditEventTypeDAO;

@Service
public class SAuditEventType implements IAuditEventType {

    @Autowired
    private AuditEventTypeDAO auditEventTypeDAO;

    @Override
    public EAuditEventType create(AuditEventTypeDTO auditEventTypeDTO) {
        EAuditEventType auditEventType = new EAuditEventType();
        auditEventType.setName(auditEventTypeDTO.getName());
        
        save(auditEventType);
        return auditEventType;
    }

    @Override
    public Optional<EAuditEventType> getById(Integer eventTypeId) {
        return auditEventTypeDAO.findById(eventTypeId);
    }

    @Override
    public EAuditEventType getById(Integer eventTypeId, Boolean handleException) {
        Optional<EAuditEventType> auditEventType = getById(eventTypeId);
        if (!auditEventType.isPresent()) {
            throw new InvalidInputException("audit event type with specified id not found", "auditEventTypeId");
        }
        return auditEventType.get();
    }

    @Override
    public Optional<EAuditEventType> getByName(String name) {
        return auditEventTypeDAO.findByName(name);
    }

    @Override
    public void save(EAuditEventType auditEventType) {
        auditEventTypeDAO.save(auditEventType);
    }
    
}
