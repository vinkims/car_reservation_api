package com.kigen.car_reservation_api.dtos.payment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsTransactionTypeNameValid;
import com.kigen.car_reservation_api.models.payment.ETransactionType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class TransactionTypeDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsTransactionTypeNameValid
    private String name;

    private String description;

    public TransactionTypeDTO(ETransactionType transactionType) {
        setCreatedOn(transactionType.getCreatedOn());
        setDescription(transactionType.getDescription());
        setId(transactionType.getId());
        setName(transactionType.getName());
    }
}
