package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.address.AreaDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.address.EArea;
import com.kigen.car_reservation_api.models.address.EMunicipality;
import com.kigen.car_reservation_api.repositories.address.AreaDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SArea implements IArea {
    
    @Autowired
    private AreaDAO areaDAO;

    @Autowired
    private IMunicipality sMunicipality;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EArea create(AreaDTO areaDTO) {

        EArea area = new EArea();
        area.setName(areaDTO.getName());
        setMunicipality(area, areaDTO.getMunicipalityId());

        save(area);
        return area;
    }

    @Override
    public Optional<EArea> getById(Integer areaId) {
        return areaDAO.findById(areaId);
    }

    @Override
    public EArea getById(Integer areaId, Boolean handleException) {
        Optional<EArea> area = getById(areaId);
        if (!area.isPresent()) {
            throw new InvalidInputException("area with specified id not found", "areaId");
        }
        return area.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EArea> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EArea> specBuilder = new SpecBuilder<EArea>();

        specBuilder = (SpecBuilder<EArea>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<EArea> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return areaDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EArea area) {
        areaDAO.save(area);
    }

    public void setMunicipality(EArea area, Integer municipalityId) {
        if (municipalityId == null) { return; }

        EMunicipality municipality = sMunicipality.getById(municipalityId, true);
        area.setMunicipality(municipality);
    }

    @Override
    public EArea update(EArea area, AreaDTO areaDTO) {
        if (areaDTO.getName() != null) {
            area.setName(areaDTO.getName());
        }
        setMunicipality(area, areaDTO.getMunicipalityId());

        save(area);
        return area;
    }
}
