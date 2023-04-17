package com.kigen.car_reservation_api.services.audit_event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDataDTO;
import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventData;
import com.kigen.car_reservation_api.repositories.audit_event.AuditEventDataDAO;

@Service
public class SAuditEventData implements IAuditEventData {

    @Autowired
    private AuditEventDataDAO eventDataDAO;

    @Override
    public EAuditEventData create(AuditEventDataDTO eventDataDTO, EAuditEvent auditEvent) {

        EAuditEventData eventData = auditEvent.getEventData() != null ? auditEvent.getEventData() : new EAuditEventData();
        eventData.setAuditEvent(auditEvent);
        eventData.setDataType(eventDataDTO.getDataType());
        eventData.setEventMessage(eventDataDTO.getEventMessage());
        eventData.setRemoteAddress(eventDataDTO.getRemoteAddress());
        eventData.setSessionId(eventDataDTO.getSessionId());

        save(eventData);
        return eventData;
    }

    @Override
    public void save(EAuditEventData eventData) {
        eventDataDAO.save(eventData);
    }
    
}
