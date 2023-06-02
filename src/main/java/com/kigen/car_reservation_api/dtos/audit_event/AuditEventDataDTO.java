package com.kigen.car_reservation_api.dtos.audit_event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AuditEventDataDTO {
    
    private String remoteAddress;

    private String sessionId;

    private String dataType;

    private String eventMessage;

    public AuditEventDataDTO(EAuditEventData eventData) {
        setDataType(eventData.getDataType());
        setEventMessage(eventData.getEventMessage());
        setRemoteAddress(eventData.getRemoteAddress());
        setSessionId(eventData.getSessionId());
    }
}
