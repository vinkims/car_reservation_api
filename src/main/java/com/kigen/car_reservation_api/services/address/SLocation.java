package com.kigen.car_reservation_api.services.address;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.address.LocationDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.address.EArea;
import com.kigen.car_reservation_api.models.address.ELocation;
import com.kigen.car_reservation_api.repositories.address.LocationDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SLocation implements ILocation {

    @Autowired
    private IArea sArea;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public ELocation create(LocationDTO locationDTO) {

        ELocation location = new ELocation();
        location.setAdditionalInfo(locationDTO.getAdditionalInfo());
        location.setCreatedOn(LocalDateTime.now());
        location.setName(locationDTO.getName());
        setArea(location, locationDTO.getAreaId());

        save(location);
        return location;
    }

    @Override
    public Optional<ELocation> getById(Integer locationId) {
        return locationDAO.findById(locationId);
    }

    @Override
    public ELocation getById(Integer locationId, Boolean handleException) {
        Optional<ELocation> location = getById(locationId);
        if (!location.isPresent() && handleException) {
            throw new InvalidInputException("location with specified id not found", "locationId");
        }
        return location.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ELocation> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ELocation> specBuilder = new SpecBuilder<ELocation>();

        specBuilder = (SpecBuilder<ELocation>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<ELocation> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return locationDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ELocation location) {
        locationDAO.save(location);
    }

    public void setArea(ELocation location, Integer areaId) {
        if (areaId == null) { return; }

        EArea area = sArea.getById(areaId, true);
        location.setArea(area);
    }

    @Override
    public ELocation update(ELocation location, LocationDTO locationDTO) {
        if (locationDTO.getAdditionalInfo() != null) {
            location.setAdditionalInfo(locationDTO.getAdditionalInfo());
        }
        if (locationDTO.getName() != null) {
            location.setName(locationDTO.getName());
        }
        setArea(location, locationDTO.getAreaId());

        save(location);
        return location;
    }
    
}
