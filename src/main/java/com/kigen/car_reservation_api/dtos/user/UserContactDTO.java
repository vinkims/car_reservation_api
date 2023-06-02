package com.kigen.car_reservation_api.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsContactValid;
import com.kigen.car_reservation_api.dtos.contact.ContactTypeDTO;
import com.kigen.car_reservation_api.models.user.EUserContact;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@IsContactValid
public class UserContactDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"regexValue", "description", "createdOn"})
    private ContactTypeDTO contactType;

    private Integer contactTypeId;

    private String contactValue;

    public UserContactDTO(EUserContact userContact) {
        setContactType(new ContactTypeDTO(userContact.getContactType()));
        setContactValue(userContact.getContactValue());
        setCreatedOn(userContact.getCreatedOn());
    }
}
