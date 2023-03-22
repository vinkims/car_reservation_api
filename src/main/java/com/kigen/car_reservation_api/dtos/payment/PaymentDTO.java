package com.kigen.car_reservation_api.dtos.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsPaymentValid;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.models.payment.EPayment;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PaymentDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    @IsPaymentValid
    private String externalTransactionId;

    private BigDecimal amount;

    private String description;

    private String reference;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private TransactionTypeDTO transactionType;

    private Integer transactionTypeId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private PaymentChannelDTO paymentChannel;

    private Integer paymentChannelId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private UserDTO user;

    private Integer userId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private StatusDTO status;

    private Integer statusId;

    public PaymentDTO(EPayment payment) {
        setAmount(payment.getAmount());
        setCreatedOn(payment.getCreatedOn());
        setDescription(payment.getDescription());
        setExternalTransactionId(payment.getExternalTransactionId());
        setId(payment.getId());
        setModifiedOn(payment.getModifiedOn());
        setPaymentChannel(new PaymentChannelDTO(payment.getPaymentChannel()));
        setReference(payment.getReference());
        setStatus(new StatusDTO(payment.getStatus()));
        setTransactionType(new TransactionTypeDTO(payment.getTransactionType()));
        if (payment.getUser() != null) {
            setUser(new UserDTO(payment.getUser()));
        };
    }
}
