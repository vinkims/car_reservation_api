package com.kigen.car_reservation_api.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDTO;
import com.kigen.car_reservation_api.services.audit_event.IAuditEvent;

@Component
public class AuditApplicationEventsListener {
    
    Logger logger = LoggerFactory.getLogger(AuditApplicationEventsListener.class);

    @Autowired
    private IAuditEvent sAuditEvent;

    @EventListener
    public void onAuditEvent(AuditApplicationEvent event) {

        AuditEvent auditEvent = event.getAuditEvent();

        logger.info("\nOn audit application event:\ntimestamp: {}\nprincipal: {}\ntype: {}\ndata: {}",
            auditEvent.getTimestamp(),
            auditEvent.getPrincipal(),
            auditEvent.getType(),
            auditEvent.getData()
        );

        try {
            AuditEventDTO auditEventDTO = new AuditEventDTO();
            auditEventDTO.setEventData(auditEvent.getData());
            auditEventDTO.setEventType(auditEvent.getType());
            auditEventDTO.setPrincipal(auditEvent.getPrincipal());
            auditEventDTO.setTimestamp(auditEvent.getTimestamp().toString());
            sAuditEvent.create(auditEventDTO);
        } catch (Exception e) {
            logger.error("\n[LOCATION] - AuditApplicationEventsListener.onAuditEvent\n[CAUSE] - {}\n[MSG] - {}",
                e.getCause(), e.getMessage());
        }
    }
}
