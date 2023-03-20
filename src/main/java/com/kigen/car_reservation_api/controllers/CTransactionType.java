package com.kigen.car_reservation_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.payment.TransactionTypeDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.ETransactionType;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.payment.ITransactionType;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Transaction Type", description = "Transaction Types Endpoints")
public class CTransactionType {
    
    @Autowired
    private ITransactionType sTransactionType;

    @PostMapping(path = "/transaction/type", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createTransactionType(@Valid @RequestBody TransactionTypeDTO transactionTypeDTO) throws URISyntaxException {

        ETransactionType transactionType = sTransactionType.create(transactionTypeDTO);

        return ResponseEntity
            .created(new URI("/transaction/type" + transactionType.getId()))
            .body(new SuccessResponse(201, "successfully created transaction type", new TransactionTypeDTO(transactionType)));
    }

    @GetMapping(path = "/transaction/type", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getTransactionTypesList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList("createdOn", "name"));

        Page<ETransactionType> transactionTypes = sTransactionType.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched transaction types", transactionTypes, 
                TransactionTypeDTO.class, ETransactionType.class));
    }

    @GetMapping(path = "/transaction/type/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getTransactionType(@PathVariable Integer id) {

        Optional<ETransactionType> transactionTypeOpt = sTransactionType.getById(id);
        if (!transactionTypeOpt.isPresent()) {
            throw new NotFoundException("transaction type with soecified id not found", "transactionTypeId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched transaction type", new TransactionTypeDTO(transactionTypeOpt.get())));
    }

    @PatchMapping(path = "/transaction/type/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateTransactionType(@PathVariable Integer id, @Valid @RequestBody TransactionTypeDTO transactionTypeDTO) {

        Optional<ETransactionType> transactionTypeOpt = sTransactionType.getById(id);
        if (!transactionTypeOpt.isPresent()) {
            throw new NotFoundException("transaction type with soecified id not found", "transactionTypeId");
        }

        ETransactionType transactionType = sTransactionType.update(transactionTypeOpt.get(), transactionTypeDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated transaction type", new TransactionTypeDTO(transactionType)));
    }
}
