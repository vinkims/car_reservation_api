package com.kigen.car_reservation_api.dtos.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.dtos.role.RoleDTO;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.models.EUser;
import com.kigen.car_reservation_api.models.EUserCivilIdentity;
import com.kigen.car_reservation_api.models.EUserContact;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    private String firstName;

    private String middleName;

    private String lastName;

    private List<@Valid UserContactDTO> contacts;

    private List<@Valid UserCivilIdentityDTO> civilIdentities;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private RoleDTO role;

    private Integer roleId;

    private String passcode;

    private Integer age;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime lastActiveOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private StatusDTO status;

    private Integer statusId;

    public UserDTO(EUser user) {
        setAge(user.getAge());
        setUserContactsData(user.getContacts());
        setCreatedOn(user.getCreatedOn());
        setFirstName(user.getFirstName());
        setId(user.getId());
        setLastActiveOn(user.getLastActiveOn());
        setMiddleName(user.getMiddleName());
        setModifiedOn(user.getModifiedOn());
        setRole(new RoleDTO(user.getRole()));
        setStatus(new StatusDTO(user.getStatus()));
    }

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    public void setUserContactsData(List<EUserContact> contactList) {
        if (contactList == null || contactList.isEmpty()) { return; }

        contacts = new ArrayList<>();
        for (EUserContact contact : contactList) {
            contacts.add(new UserContactDTO(contact));
        }
    }

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    public void setUserCivilIdentityData(List<EUserCivilIdentity> civilIdentitiesList) {
        if (civilIdentitiesList == null || civilIdentitiesList.isEmpty()) { return; }

        civilIdentities = new ArrayList<>();
        for (EUserCivilIdentity civilIdentity : civilIdentitiesList) {
            civilIdentities.add(new UserCivilIdentityDTO(civilIdentity));
        }
    }
}