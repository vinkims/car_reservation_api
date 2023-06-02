package com.kigen.car_reservation_api.controllers.payment;

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
import com.kigen.car_reservation_api.dtos.payment.PaymentDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.payment.EPayment;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.payment.IPayment;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Payments", description = "Payment Endpoints")
public class CPayment {
    
    @Autowired
    private IPayment sPayment;

    @PostMapping(path = "/payment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) throws URISyntaxException {

        EPayment payment = sPayment.create(paymentDTO);

        return ResponseEntity
            .created(new URI("/payment/" + payment.getId()))
            .body(new SuccessResponse(201, "successfully created payment", new PaymentDTO(payment)));
    }

    @GetMapping(path = "/payment", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPayments(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList(
            "createdOn", "modifiedOn", "amount", "reference", "transactionType.id", "paymentChannel.id", "status.id",
            "user.id", "externalTransactionId"
        ));

        Page<EPayment> payments = sPayment.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched payments", payments, 
                PaymentDTO.class, EPayment.class));
    }

    @GetMapping(path = "/payment/{paymentId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getPayment(@PathVariable Integer paymentId) {

        Optional<EPayment> paymentOpt = sPayment.getById(paymentId);
        if (!paymentOpt.isPresent()) {
            throw new NotFoundException("payment with specified id not found", "paymentId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched payment", new PaymentDTO(paymentOpt.get())));
    }

    @PatchMapping(path = "/payment/{paymentId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updatePayment(@PathVariable Integer paymentId, @RequestBody PaymentDTO paymentDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Optional<EPayment> paymentOpt = sPayment.getById(paymentId);
        if (!paymentOpt.isPresent()) {
            throw new NotFoundException("payment with specified id not found", "paymentId");
        }
        
        EPayment payment = sPayment.update(paymentOpt.get(), paymentDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated payment", new PaymentDTO(payment)));
    }
}
