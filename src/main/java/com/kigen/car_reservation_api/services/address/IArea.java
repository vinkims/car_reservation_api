package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.address.AreaDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.address.EArea;

public interface IArea {
    
    EArea create(AreaDTO areaDTO);

    Optional<EArea> getById(Integer areaId);

    EArea getById(Integer areaId, Boolean handleException);

    Page<EArea> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EArea area);

    EArea update(EArea area, AreaDTO areaDTO);
}
