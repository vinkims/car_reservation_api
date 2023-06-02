package com.kigen.car_reservation_api.dtos.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsCivilIdentityValid;
import com.kigen.car_reservation_api.dtos.civilIdentity.CivilIdentityTypeDTO;
import com.kigen.car_reservation_api.models.user.EUserCivilIdentity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserCivilIdentityDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"description", "createdOn"})
    private CivilIdentityTypeDTO civilIdentityType;

    private Integer civilIdentityTypeId;

    @IsCivilIdentityValid
    private String civilIdentityValue;

    public UserCivilIdentityDTO(EUserCivilIdentity userCivilIdentity) {
        setCivilIdentityType(new CivilIdentityTypeDTO(userCivilIdentity.getCivilIdentityType()));
        setCivilIdentityValue(userCivilIdentity.getCivilIdentityValue());
        setCreatedOn(userCivilIdentity.getCreatedOn());
    } 
}
