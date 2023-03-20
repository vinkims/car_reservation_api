package com.kigen.car_reservation_api.services.contact;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.contact.ContactTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.EContactType;

public interface IContactType {
    
    Boolean checkExistsByName(String name);

    EContactType create(ContactTypeDTO contactTypeDTO);

    Optional<EContactType> getById(Integer contactTypeId);

    EContactType getById(Integer contactTypeId, Boolean handleException);

    Page<EContactType> getPaginatedList(PageDTO pageDTO, List<String> allowableFileds);

    void save(EContactType contactType);

    EContactType update(EContactType contactType, ContactTypeDTO contactTypeDTO) throws IllegalAccessException, 
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
