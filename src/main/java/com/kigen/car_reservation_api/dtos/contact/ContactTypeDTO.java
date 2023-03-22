package com.kigen.car_reservation_api.dtos.contact;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsContactTypeNameValid;
import com.kigen.car_reservation_api.models.user.EContactType;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ContactTypeDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsContactTypeNameValid
    private String name;

    private String description;

    private String regexValue;

    public ContactTypeDTO(EContactType contactType) {
        setCreatedOn(contactType.getCreatedOn());
        setDescription(contactType.getDescription());
        setId(contactType.getId());
        setName(contactType.getName());
        setRegexValue(contactType.getRegexValue());
    }
}
