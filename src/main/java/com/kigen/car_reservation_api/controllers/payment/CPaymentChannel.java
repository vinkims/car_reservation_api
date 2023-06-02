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
import com.kigen.car_reservation_api.dtos.payment.PaymentChannelDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.payment.EPaymentChannel;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.payment.IPaymentChannel;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Payment Channels", description = "Payment Channel Endpoints")
public class CPaymentChannel {
    
    @Autowired
    private IPaymentChannel sPaymentChannel;

    @PostMapping(path = "/payment/channel", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createPaymentChannel(@Valid @RequestBody PaymentChannelDTO paymentChannelDTO) 
            throws URISyntaxException {

        EPaymentChannel paymentChannel = sPaymentChannel.create(paymentChannelDTO);

        return ResponseEntity
            .created(new URI("/payment/channel/" + paymentChannel.getId()))
            .body(new SuccessResponse(201, "successfully created payment channel", new PaymentChannelDTO(paymentChannel)));
    }

    @GetMapping(path = "/payment/channel", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaymentChannelsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList("createdOn", "name"));

        Page<EPaymentChannel> paymentChannels = sPaymentChannel.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched payment channels", paymentChannels, 
                PaymentChannelDTO.class, EPaymentChannel.class));
    }

    @GetMapping(path = "/payment/channel/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getPaymentChannel(@PathVariable Integer id) {

        Optional<EPaymentChannel> paymentChannelOpt = sPaymentChannel.getById(id);
        if (!paymentChannelOpt.isPresent()) {
            throw new NotFoundException("payment channel with specified id not found", "paymentChannelId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched payment channel", new PaymentChannelDTO(paymentChannelOpt.get())));
    }

    @PatchMapping(path = "/payment/channel/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updatePaymentChannel(@PathVariable Integer id, @Valid @RequestBody PaymentChannelDTO paymentChannelDTO) {

        Optional<EPaymentChannel> paymentChannelOpt = sPaymentChannel.getById(id);
        if (!paymentChannelOpt.isPresent()) {
            throw new NotFoundException("payment channel with specified id not found", "paymentChannelId");
        }

        EPaymentChannel paymentChannel = sPaymentChannel.update(paymentChannelOpt.get(), paymentChannelDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated payment channe;", new PaymentChannelDTO(paymentChannel)));
    }
}
