package com.kigen.car_reservation_api.services.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.payment.TransactionTypeDTO;
import com.kigen.car_reservation_api.models.ETransactionType;

public interface ITransactionType {
    
    Boolean checkExistsByName(String name);

    ETransactionType create(TransactionTypeDTO transactionTypeDTO);

    Optional<ETransactionType> getById(Integer transactionTypeId);

    ETransactionType getById(Integer transactionTypeId, Boolean handleException);

    Page<ETransactionType> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ETransactionType transactionType);

    ETransactionType update(ETransactionType transactionType, TransactionTypeDTO transactionTypeDTO);
}
