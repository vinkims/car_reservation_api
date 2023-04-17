package com.kigen.car_reservation_api.dtos.audit_event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AuditEventTypeDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    private String name;

    public AuditEventTypeDTO(EAuditEventType auditEventType) {
        setId(auditEventType.getId());
        setName(auditEventType.getName());
    }
}
