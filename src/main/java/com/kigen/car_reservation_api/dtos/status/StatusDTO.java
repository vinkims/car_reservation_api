package com.kigen.car_reservation_api.dtos.status;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsStatusNameValid;
import com.kigen.car_reservation_api.models.EStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class StatusDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsStatusNameValid
    private String name;

    private String description;

    public StatusDTO(EStatus status) {
        setCreatedOn(status.getCreatedOn());
        setDescription(status.getDescription());
        setId(status.getId());
        setName(status.getName());
    }
}
