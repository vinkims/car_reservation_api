package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.address.MunicipalityDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.address.EMunicipality;

public interface IMunicipality {
    
    EMunicipality create(MunicipalityDTO municipalityDTO);

    Optional<EMunicipality> getById(Integer municipalityId);

    EMunicipality getById(Integer municipalityId, Boolean handleException);

    Page<EMunicipality> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EMunicipality municipality);

    EMunicipality update(EMunicipality municipality, MunicipalityDTO municipalityDTO);
}
