package com.kigen.car_reservation_api.dtos.audit_event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

    private String principal;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties("id")
    private AuditEventTypeDTO eventType;

    private AuditEventDataDTO eventData;

    private String auditEventTypeName;

    private Integer statusId;

    public AuditEventDTO(EAuditEvent auditEvent) {
        setCreatedOn(auditEvent.getCreatedOn());
        if (auditEvent.getEventData() != null) {
            setEventData(new AuditEventDataDTO(auditEvent.getEventData()));
        }
        if (auditEvent.getEventType() != null) {
            setEventType(new AuditEventTypeDTO(auditEvent.getEventType()));
        }
        setId(auditEvent.getId());
        setPrincipal(auditEvent.getPrincipal());
    }
}
