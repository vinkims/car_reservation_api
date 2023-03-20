package com.kigen.car_reservation_api.services.vehicle;

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
import com.kigen.car_reservation_api.dtos.vehicle.FuelTypeDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.EFuelType;
import com.kigen.car_reservation_api.repositories.FuelTypeDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SFuelType implements IFuelType {
    
    @Autowired
    private FuelTypeDAO fuelTypeDAO;

    @Autowired
    private SpecFactory specFactory;
    
    @Override
    public Boolean checkExistsByName(String name) {
        return fuelTypeDAO.existsByName(name);
    }

    @Override
    public EFuelType create(FuelTypeDTO fuelTypeDTO) {

        EFuelType fuelType = new EFuelType();
        fuelType.setCreatedOn(LocalDateTime.now());
        fuelType.setDescription(fuelTypeDTO.getDescription());
        fuelType.setName(fuelTypeDTO.getName());

        save(fuelType);
        return fuelType;
    }

    @Override
    public Optional<EFuelType> getById(Integer fuelTypeId) {
        return fuelTypeDAO.findById(fuelTypeId);
    }

    @Override
    public EFuelType getById(Integer fuelTypeId, Boolean handleException) {
        Optional<EFuelType> fuelType = getById(fuelTypeId);
        if (!fuelType.isPresent() && handleException) {
            throw new InvalidInputException("fuel type with specified id not found", "fuelTypeId");
        }
        return fuelType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EFuelType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EFuelType> specBuilder = new SpecBuilder<EFuelType>();

        specBuilder = (SpecBuilder<EFuelType>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<EFuelType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return fuelTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EFuelType fuelType) {
        fuelTypeDAO.save(fuelType);
    }

    @Override
    public EFuelType update(EFuelType fuelType, FuelTypeDTO fuelTypeDTO) {
        if (fuelTypeDTO.getDescription() != null) {
            fuelType.setDescription(fuelTypeDTO.getDescription());
        }
        if (fuelTypeDTO.getName() != null) {
            fuelType.setName(fuelTypeDTO.getName());
        }

        save(fuelType);
        return fuelType;
    }
}
