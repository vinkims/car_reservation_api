package com.kigen.car_reservation_api.services.payment;

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
import com.kigen.car_reservation_api.dtos.payment.TransactionTypeDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.payment.ETransactionType;
import com.kigen.car_reservation_api.repositories.payment.TransactionTypeDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class STransactionType implements ITransactionType {

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private TransactionTypeDAO transactionTypeDAO;

    @Override
    public Boolean checkExistsByName(String name) {
        return transactionTypeDAO.existsByName(name);
    }

    @Override
    public ETransactionType create(TransactionTypeDTO transactionTypeDTO) {

        ETransactionType transactionType = new ETransactionType();
        transactionType.setCreatedOn(LocalDateTime.now());
        transactionType.setDescription(transactionTypeDTO.getDescription());
        transactionType.setName(transactionTypeDTO.getName());

        save(transactionType);
        return transactionType;
    }

    @Override
    public Optional<ETransactionType> getById(Integer transactionTypeId) {
        return transactionTypeDAO.findById(transactionTypeId);
    }

    @Override
    public ETransactionType getById(Integer transactionTypeId, Boolean handleException) {
        Optional<ETransactionType> transactionType = getById(transactionTypeId);
        if (!transactionType.isPresent() && handleException) {
            throw new InvalidInputException("transaction type with specified id not found", "transactiontypeId");
        }
        return transactionType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ETransactionType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ETransactionType> specBuilder = new SpecBuilder<ETransactionType>();

        specBuilder = (SpecBuilder<ETransactionType>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<ETransactionType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return transactionTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ETransactionType transactionType) {
        transactionTypeDAO.save(transactionType);
    }

    @Override
    public ETransactionType update(ETransactionType transactionType, TransactionTypeDTO transactionTypeDTO) {
        if (transactionTypeDTO.getDescription() != null) {
            transactionType.setDescription(transactionTypeDTO.getDescription());
        }
        if (transactionTypeDTO.getName() != null) {
            transactionType.setName(transactionTypeDTO.getName());
        }

        save(transactionType);
        return transactionType;
    }
    
}
