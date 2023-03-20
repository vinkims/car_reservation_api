package com.kigen.car_reservation_api.dtos.payment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsPaymentChannelNameValid;
import com.kigen.car_reservation_api.models.EPaymentChannel;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PaymentChannelDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsPaymentChannelNameValid
    private String name;

    private String description;

    public PaymentChannelDTO(EPaymentChannel paymentChannel) {
        setCreatedOn(paymentChannel.getCreatedOn());
        setDescription(paymentChannel.getDescription());
        setId(paymentChannel.getId());
        setName(paymentChannel.getName());
    }
}
