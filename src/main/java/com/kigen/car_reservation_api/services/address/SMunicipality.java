package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.address.MunicipalityDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.address.EMunicipality;
import com.kigen.car_reservation_api.models.address.ERegion;
import com.kigen.car_reservation_api.repositories.address.MunicipalityDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SMunicipality implements IMunicipality {

    @Autowired
    private IRegion sRegion;

    @Autowired
    private MunicipalityDAO municipalityDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EMunicipality create(MunicipalityDTO municipalityDTO) {

        EMunicipality municipality = new EMunicipality();
        municipality.setName(municipalityDTO.getName());
        setRegion(municipality, municipalityDTO.getRegionId());

        save(municipality);
        return municipality;
    }

    @Override
    public Optional<EMunicipality> getById(Integer municipalityId) {
        return municipalityDAO.findById(municipalityId);
    }

    @Override
    public EMunicipality getById(Integer municipalityId, Boolean handleException) {
        Optional<EMunicipality> municipality = getById(municipalityId);
        if (!municipality.isPresent() && handleException) {
            throw new InvalidInputException("municipality with specified id not found", "municipalityId");
        }
        return municipality.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EMunicipality> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EMunicipality> specBuilder = new SpecBuilder<EMunicipality>();

        specBuilder = (SpecBuilder<EMunicipality>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<EMunicipality> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return municipalityDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMunicipality municipality) {
        municipalityDAO.save(municipality);
    }

    public void setRegion(EMunicipality municipality, Integer regionId) {
        if (regionId == null) { return; }

        ERegion region = sRegion.getById(regionId, true);
        municipality.setRegion(region);
    }

    @Override
    public EMunicipality update(EMunicipality municipality, MunicipalityDTO municipalityDTO) {
        if (municipalityDTO.getName() != null) {
            municipality.setName(municipalityDTO.getName());
        }
        setRegion(municipality, municipalityDTO.getRegionId());

        save(municipality);
        return municipality;
    }
    
}
