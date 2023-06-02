package com.kigen.car_reservation_api.dtos.role;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.annotations.IsRoleNameValid;
import com.kigen.car_reservation_api.models.user.ERole;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RoleDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @IsRoleNameValid
    private String name;

    private String description;

    public RoleDTO(ERole role) {
        setCreatedOn(role.getCreatedOn());
        setDescription(role.getDescription());
        setId(role.getId());
        setName(role.getName());
    }
}
