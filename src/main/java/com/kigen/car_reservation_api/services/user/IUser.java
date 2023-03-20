package com.kigen.car_reservation_api.services.user;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.models.EUser;

public interface IUser {
    
    EUser create(UserDTO userDTO);

    Optional<EUser> getByContactValue(String contactValue);

    Optional<EUser> getById(Integer userId);

    EUser getById(Integer userId, Boolean handleException);

    Optional<EUser> getByIdOrContactValue(String userValue);

    Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EUser user);

    EUser update(EUser user, UserDTO userDTO) throws IllegalAccessException, IllegalArgumentException, 
        InvocationTargetException, NoSuchMethodException, SecurityException;
}
