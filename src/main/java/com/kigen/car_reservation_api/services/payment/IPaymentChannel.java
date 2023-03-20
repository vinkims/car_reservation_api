package com.kigen.car_reservation_api.services.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.payment.PaymentChannelDTO;
import com.kigen.car_reservation_api.models.EPaymentChannel;

public interface IPaymentChannel {
    
    Boolean checkExistsByName(String name);

    EPaymentChannel create(PaymentChannelDTO paymentChannelDTO);

    Optional<EPaymentChannel> getById(Integer paymentChannelId);

    EPaymentChannel getById(Integer paymentChannelId, Boolean handleException);

    Page<EPaymentChannel> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EPaymentChannel paymentChannel);

    EPaymentChannel update(EPaymentChannel paymentChannel, PaymentChannelDTO paymentChannelDTO);
}
