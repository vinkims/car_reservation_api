package com.kigen.car_reservation_api.services.status;

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
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.EStatus;
import com.kigen.car_reservation_api.repositories.StatusDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SStatus implements IStatus {

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private StatusDAO statusDAO;

    @Override
    public Boolean checkExistsByName(String name) {
        return statusDAO.existsByName(name);
    }

    @Override
    public EStatus create(StatusDTO statusDTO) {
        
        EStatus status = new EStatus();
        status.setCreatedOn(LocalDateTime.now());
        status.setDescription(statusDTO.getDescription());
        status.setName(statusDTO.getName());

        save(status);
        return status;
    }

    @Override
    public Optional<EStatus> getById(Integer statusId) {
        return statusDAO.findById(statusId);
    }

    @Override
    public EStatus getById(Integer statusId, Boolean handleException) {
        Optional<EStatus> status = getById(statusId);
        if (!status.isPresent() && handleException) {
            throw new InvalidInputException("status with specified id not found", "statusId");
        }
        return status.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EStatus> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EStatus> specBuilder = new SpecBuilder<EStatus>();

        specBuilder = (SpecBuilder<EStatus>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<EStatus> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return statusDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EStatus status) {
        statusDAO.save(status);
    }

    @Override
    public EStatus update(EStatus status, StatusDTO statusDTO) {
        if (statusDTO.getDescription() != null) {
            status.setDescription(statusDTO.getDescription());
        }
        if (statusDTO.getName() != null) {
            status.setName(statusDTO.getName());
        }

        save(status);
        return status;
    }
    
}
