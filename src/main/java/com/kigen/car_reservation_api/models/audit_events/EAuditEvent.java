package com.kigen.car_reservation_api.models.audit_events;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.kigen.car_reservation_api.models.status.EStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "audit_events")
@Data
@NoArgsConstructor
public class EAuditEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @OneToOne(mappedBy = "auditEvent")
    private EAuditEventData eventData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    private EAuditEventType eventType;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "principal")
    private String principal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;
}
