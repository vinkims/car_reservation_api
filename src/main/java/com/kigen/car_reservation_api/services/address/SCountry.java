package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.address.CountryDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.address.ECountry;
import com.kigen.car_reservation_api.repositories.address.CountryDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SCountry implements ICountry {
    
    @Autowired
    private CountryDAO countryDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ECountry create(CountryDTO countryDTO) {

        ECountry country = new ECountry();
        country.setCurrency(countryDTO.getCurrency());
        country.setName(countryDTO.getName());

        save(country);
        return country;
    }

    @Override
    @Caching(cacheable = {@Cacheable(cacheNames = "countries", unless = "#result == null")})
    public Optional<ECountry> getById(Integer countryId) {
        return countryDAO.findById(countryId);
    }

    @Override
    public ECountry getById(Integer countryId, Boolean handleException) {
        Optional<ECountry> country = getById(countryId);
        if (!country.isPresent() && handleException) {
            throw new InvalidInputException("country with specified id not found", "countryId");
        }
        return country.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECountry> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<ECountry> specBuilder = new SpecBuilder<ECountry>();

        specBuilder = (SpecBuilder<ECountry>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<ECountry> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return countryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECountry country) {
        countryDAO.save(country);
    }

    @Override
    public ECountry update(ECountry country, CountryDTO countryDTO) {
        if (countryDTO.getCurrency() != null) {
            country.setCurrency(countryDTO.getCurrency());
        }
        if (countryDTO.getName() != null) {
            country.setName(countryDTO.getName());
        }

        save(country);
        return country;
    }
}
