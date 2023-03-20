package com.kigen.car_reservation_api.services.civilIdentity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.civilIdentity.CivilIdentityTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.ECivilIdentityType;

public interface ICivilIdentityType {
    
    Boolean checkExistsByName(String name);

    ECivilIdentityType create(CivilIdentityTypeDTO civilIdentityTypeDTO);

    Optional<ECivilIdentityType> getById(Integer civilIdentityTypeId);

    ECivilIdentityType getById(Integer civilIdentityTypeId, Boolean handleException);

    Page<ECivilIdentityType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ECivilIdentityType civilIdentityType);

    ECivilIdentityType update(ECivilIdentityType civilIdentityType, CivilIdentityTypeDTO civilIdentityTypeDTO);
}
