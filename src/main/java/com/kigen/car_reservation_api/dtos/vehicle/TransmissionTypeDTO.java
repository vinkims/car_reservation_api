package com.kigen.car_reservation_api.dtos.vehicle;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsTransmissionTypeNameValid;
import com.kigen.car_reservation_api.models.ETransmissionType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class TransmissionTypeDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsTransmissionTypeNameValid
    private String name;

    private String description;

    public TransmissionTypeDTO(ETransmissionType transmissionType) {
        setCreatedOn(transmissionType.getCreatedOn());
        setDescription(transmissionType.getDescription());
        setId(transmissionType.getId());
        setName(transmissionType.getName());
    }
}
