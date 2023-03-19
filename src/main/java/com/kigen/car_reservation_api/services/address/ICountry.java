package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.address.CountryDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.ECountry;

public interface ICountry {
    
    ECountry create(CountryDTO countryDTO);

    Optional<ECountry> getById(Integer countryId);

    ECountry getById(Integer countryId, Boolean handleException);

    Page<ECountry> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ECountry country);

    ECountry update(ECountry country, CountryDTO countryDTO);
}
