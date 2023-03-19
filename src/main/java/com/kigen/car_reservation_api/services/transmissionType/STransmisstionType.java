package com.kigen.car_reservation_api.services.transmissionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.transmissionType.TransmissionTypeDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.ETransmissionType;
import com.kigen.car_reservation_api.repositories.TransmissionTypeDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class STransmisstionType implements ITransmissionType {

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private TransmissionTypeDAO transmissionTypeDAO;

    @Override
    public Boolean checkExistsByName(String name) {
        return transmissionTypeDAO.existsByName(name);
    }

    @Override
    public ETransmissionType create(TransmissionTypeDTO transmissionTypeDTO) {
        
        ETransmissionType transmissionType = new ETransmissionType();
        transmissionType.setCreatedOn(LocalDateTime.now());
        transmissionType.setDescription(transmissionTypeDTO.getDescription());
        transmissionType.setName(transmissionTypeDTO.getName());

        save(transmissionType);
        return transmissionType;
    }

    @Override
    public Optional<ETransmissionType> getById(Integer transmissionTypeId) {
        return transmissionTypeDAO.findById(transmissionTypeId);
    }

    @Override
    public ETransmissionType getById(Integer transmissionTypeId, Boolean handleException) {
        Optional<ETransmissionType> transmissionType = getById(transmissionTypeId);
        if (!transmissionType.isPresent() && handleException) {
            throw new InvalidInputException("transmission type with specified id not found", "transmissionTypeId");
        }
        return transmissionType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ETransmissionType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ETransmissionType> specBuilder = new SpecBuilder<ETransmissionType>();

        specBuilder = (SpecBuilder<ETransmissionType>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<ETransmissionType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return transmissionTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ETransmissionType transmissionType) {
        transmissionTypeDAO.save(transmissionType);
    }

    @Override
    public ETransmissionType update(ETransmissionType transmissionType, TransmissionTypeDTO transmissionTypeDTO) {
        if (transmissionTypeDTO.getDescription() != null) {
            transmissionType.setDescription(transmissionTypeDTO.getDescription());
        }
        if (transmissionTypeDTO.getName() != null) {
            transmissionType.setName(transmissionTypeDTO.getName());
        }
        
        save(transmissionType);
        return transmissionType;
    }
    
}
