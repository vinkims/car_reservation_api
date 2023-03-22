package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.address.RegionDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.address.ERegion;

public interface IRegion {
    
    ERegion create(RegionDTO regionDTO);

    Optional<ERegion> getById(Integer regionId);

    ERegion getById(Integer regionId, Boolean handleException);

    Page<ERegion> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ERegion region);

    ERegion update(ERegion region, RegionDTO regionDTO);
}
