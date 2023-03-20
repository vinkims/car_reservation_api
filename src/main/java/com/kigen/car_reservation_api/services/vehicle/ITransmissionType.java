package com.kigen.car_reservation_api.services.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.TransmissionTypeDTO;
import com.kigen.car_reservation_api.models.ETransmissionType;

public interface ITransmissionType {
    
    Boolean checkExistsByName(String name);

    ETransmissionType create(TransmissionTypeDTO transmissionTypeDTO);

    Optional<ETransmissionType> getById(Integer transmissionTypeId);

    ETransmissionType getById(Integer transmissionTypeId, Boolean handleException);

    Page<ETransmissionType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ETransmissionType transmissionType);

    ETransmissionType update(ETransmissionType transmissionType, TransmissionTypeDTO transmissionTypeDTO);
}
