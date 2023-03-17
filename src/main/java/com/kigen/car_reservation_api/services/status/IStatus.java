package com.kigen.car_reservation_api.services.status;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.models.EStatus;

public interface IStatus {
    
    Boolean checkExistsByName(String name);

    EStatus create(StatusDTO statusDTO);

    Optional<EStatus> getById(Integer statusId);

    EStatus getById(Integer statusId, Boolean handleException);

    Page<EStatus> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EStatus status);

    EStatus update(EStatus status, StatusDTO statusDTO);
}
