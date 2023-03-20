package com.kigen.car_reservation_api.services.civilIdentity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.civilIdentity.CivilIdentityTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.ECivilIdentityType;
import com.kigen.car_reservation_api.repositories.CivilIdentityTypeDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SCivilIdentityType implements ICivilIdentityType {

    @Autowired
    private CivilIdentityTypeDAO civilIdentityTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return civilIdentityTypeDAO.existsByName(name);
    }

    @Override
    public ECivilIdentityType create(CivilIdentityTypeDTO civilIdentityTypeDTO) {
        
        ECivilIdentityType civilIdentityType = new ECivilIdentityType();
        civilIdentityType.setDescription(civilIdentityTypeDTO.getDescription());
        civilIdentityType.setName(civilIdentityTypeDTO.getName());

        save(civilIdentityType);
        return civilIdentityType;
    }

    @Override
    public Optional<ECivilIdentityType> getById(Integer civilIdentityTypeId) {
        return civilIdentityTypeDAO.findById(civilIdentityTypeId);
    }

    @Override
    public ECivilIdentityType getById(Integer civilIdentityTypeId, Boolean handleException) {
        Optional<ECivilIdentityType> civilIdentityType = getById(civilIdentityTypeId);
        if (!civilIdentityType.isPresent() && handleException) {
            throw new InvalidInputException("civil identity type with specified id not found", "civilIdentityTypeId");
        }
        return civilIdentityType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ECivilIdentityType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ECivilIdentityType> specBuilder = new SpecBuilder<ECivilIdentityType>();

        specBuilder = (SpecBuilder<ECivilIdentityType>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<ECivilIdentityType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return civilIdentityTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ECivilIdentityType civilIdentityType) {
        civilIdentityTypeDAO.save(civilIdentityType);
    }

    @Override
    public ECivilIdentityType update(ECivilIdentityType civilIdentityType, CivilIdentityTypeDTO civilIdentityTypeDTO) {
        if (civilIdentityTypeDTO.getDescription() != null) {
            civilIdentityType.setDescription(civilIdentityTypeDTO.getDescription());
        }
        if (civilIdentityTypeDTO.getName() != null) {
            civilIdentityType.setName(civilIdentityTypeDTO.getName());
        }
        
        save(civilIdentityType);
        return civilIdentityType;
    }
    
}
