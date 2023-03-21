package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.address.RegionDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.address.ECountry;
import com.kigen.car_reservation_api.models.address.ERegion;
import com.kigen.car_reservation_api.repositories.address.RegionDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SRegion implements IRegion {
    
    @Autowired
    private ICountry sCountry;

    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ERegion create(RegionDTO regionDTO) {

        ERegion region = new ERegion();
        region.setName(regionDTO.getName());
        setCountry(region, regionDTO.getCountryId());

        save(region);
        return region;
    }

    @Override
    public Optional<ERegion> getById(Integer regionId) {
        return regionDAO.findById(regionId);
    }

    @Override
    public ERegion getById(Integer regionId, Boolean handleException) {
        Optional<ERegion> region = getById(regionId);
        if (!region.isPresent() && handleException) {
            throw new InvalidInputException("region with specified id not found", "regionId");
        }
        return region.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ERegion> getPaginatedList(PageDTO pageDTO, List<String> allowableFIelds) {

        String search = pageDTO.getSearch();

        SpecBuilder<ERegion> specBuilder = new SpecBuilder<ERegion>();

        specBuilder = (SpecBuilder<ERegion>) specFactory.generateSpecification(
            search, specBuilder, allowableFIelds);

        Specification<ERegion> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return regionDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ERegion region) {
        regionDAO.save(region);
    }

    public void setCountry(ERegion region, Integer countryId) {
        if (countryId == null) { return; }

        ECountry country = sCountry.getById(countryId, true);
        region.setCountry(country);
    }

    @Override
    public ERegion update(ERegion region, RegionDTO regionDTO) {
        if (regionDTO.getName() != null) {
            region.setName(regionDTO.getName());
        }
        setCountry(region, regionDTO.getCountryId());

        save(region);
        return region;
    }
}
