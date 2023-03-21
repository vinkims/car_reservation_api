package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.address.LocationDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.address.ELocation;

public interface ILocation {
    
    ELocation create(LocationDTO locationDTO);

    Optional<ELocation> getById(Integer locationId);

    ELocation getById(Integer locationId, Boolean handleException);

    Page<ELocation> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ELocation location);

    ELocation update(ELocation location, LocationDTO locationDTO);
}
