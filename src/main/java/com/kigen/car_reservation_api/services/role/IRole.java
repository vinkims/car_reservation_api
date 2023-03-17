package com.kigen.car_reservation_api.services.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.role.RoleDTO;
import com.kigen.car_reservation_api.models.ERole;

public interface IRole {
    
    Boolean checkExistsByName(String name);

    ERole create(RoleDTO roleDTO);

    Optional<ERole> getById(Integer roleId);

    ERole getById(Integer roleId, Boolean handleException);

    Page<ERole> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(ERole role);

    ERole update(ERole role, RoleDTO roleDTO);
}
