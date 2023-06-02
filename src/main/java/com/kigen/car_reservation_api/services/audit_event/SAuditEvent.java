package com.kigen.car_reservation_api.services.audit_event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDTO;
import com.kigen.car_reservation_api.dtos.audit_event.AuditEventDataDTO;
import com.kigen.car_reservation_api.dtos.audit_event.AuditEventTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.audit_events.EAuditEvent;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventData;
import com.kigen.car_reservation_api.models.audit_events.EAuditEventType;
import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.repositories.audit_event.AuditEventDAO;
import com.kigen.car_reservation_api.services.status.IStatus;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SAuditEvent implements IAuditEvent {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private AuditEventDAO auditEventDAO;

    @Autowired
    private IAuditEventData sAuditEventData;

    @Autowired
    private IAuditEventType sAuditEventType;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EAuditEvent create(AuditEventDTO auditEventDTO) {

        EAuditEvent auditEvent = new EAuditEvent();
        auditEvent.setCreatedOn(LocalDateTime.now());
        auditEvent.setPrincipal(auditEventDTO.getPrincipal());
        setEventType(auditEvent, auditEventDTO);
        Integer statusId = auditEventDTO.getStatusId() == null ? activeStatusId : auditEventDTO.getStatusId();
        setStatus(auditEvent, statusId);

        save(auditEvent);
        setEventData(auditEvent, auditEventDTO.getEventData());

        return auditEvent;
    }

    @Override
    public Optional<EAuditEvent> getById(Integer eventId) {
        return auditEventDAO.findById(eventId);
    }

    @Override
    public EAuditEvent getById(Integer eventId, Boolean handleException) {
        Optional<EAuditEvent> auditEvent = getById(eventId);
        if (!auditEvent.isPresent() && handleException) {
            throw new InvalidInputException("audit event with specified id not found", "auditEventId");
        }
        return auditEvent.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EAuditEvent> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EAuditEvent> specBuilder = new SpecBuilder<EAuditEvent>();

        specBuilder = (SpecBuilder<EAuditEvent>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<EAuditEvent> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return auditEventDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EAuditEvent auditEvent) {
        auditEventDAO.save(auditEvent);
    }

    public void setEventData(EAuditEvent auditEvent, AuditEventDataDTO eventDataDTO) {
        if (eventDataDTO == null) { return; }

        EAuditEventData eventData = sAuditEventData.create(eventDataDTO, auditEvent);
        auditEvent.setEventData(eventData);
    }

    public void setEventType(EAuditEvent auditEvent, AuditEventDTO auditEventDTO) {
        if (auditEventDTO.getAuditEventTypeName() == null) { return; }

        String eventTypeName = auditEventDTO.getAuditEventTypeName();
        Optional<EAuditEventType> auditEventTypeOpt = sAuditEventType.getByName(eventTypeName);
        EAuditEventType auditEventType;
        if (auditEventTypeOpt.isPresent()) {
            auditEventType = auditEventTypeOpt.get();
        } else {
            // Create new event type 
            AuditEventTypeDTO eventTypeDTO = new AuditEventTypeDTO();
            eventTypeDTO.setName(eventTypeName);
            auditEventType = sAuditEventType.create(eventTypeDTO);
        }
        auditEvent.setEventType(auditEventType);
    }

    public void setStatus(EAuditEvent auditEvent, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        auditEvent.setStatus(status);
    }

    @Override
    public EAuditEvent update(EAuditEvent auditEvent, AuditEventDTO auditEventDTO) {

        if (auditEventDTO.getPrincipal() != null) {
            auditEvent.setPrincipal(auditEventDTO.getPrincipal());
        }

        setEventType(auditEvent, auditEventDTO);
        setStatus(auditEvent, auditEventDTO.getStatusId());

        save(auditEvent);
        setEventData(auditEvent, auditEventDTO.getEventData());

        return auditEvent;
    }
    
}
