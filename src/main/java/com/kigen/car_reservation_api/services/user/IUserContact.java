package com.kigen.car_reservation_api.services.user;

import java.util.Optional;

import com.kigen.car_reservation_api.dtos.user.UserContactDTO;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.models.user.EUserContact;

public interface IUserContact {

    Boolean checkExistsByContactValue(String contactValue);
    
    EUserContact create(EUser user, UserContactDTO userContactDTO);

    Optional<EUserContact> getByValue(String contactValue);

    EUserContact getByValue(String contactValue, Boolean handleException);

    void save(EUserContact userContact);
}
