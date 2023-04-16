package com.kigen.car_reservation_api.dtos.audit_event;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AuditEventDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    private String timestamp;

    private String principal;

    private String eventType;

    private Map<String, Object> eventData;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private StatusDTO status;

    private Integer statusId;

    public AuditEventDTO(EAuditEvent auditEvent) {
        setCreatedOn(auditEvent.getCreatedOn());
        setEventData(auditEvent.getEventData());
        setEventType(auditEvent.getEventType());
        setId(auditEvent.getId());
        setPrincipal(auditEvent.getPrincipal());
        setStatus(new StatusDTO(auditEvent.getStatus()));
        setTimestamp(auditEvent.getTimestamp());
    }
}
