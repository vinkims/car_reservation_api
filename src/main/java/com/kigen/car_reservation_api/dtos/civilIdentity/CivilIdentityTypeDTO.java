package com.kigen.car_reservation_api.dtos.civilIdentity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsCivilIdentityTypeNameValid;
import com.kigen.car_reservation_api.models.ECivilIdentityType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CivilIdentityTypeDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsCivilIdentityTypeNameValid
    private String name;

    private String description;

    public CivilIdentityTypeDTO(ECivilIdentityType civilIdentityType) {
        setCreatedOn(civilIdentityType.getCreatedOn());
        setDescription(civilIdentityType.getDescription());
        setId(civilIdentityType.getId());
        setName(civilIdentityType.getName());
    }
}
