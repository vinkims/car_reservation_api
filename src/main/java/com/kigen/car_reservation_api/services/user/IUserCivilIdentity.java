package com.kigen.car_reservation_api.services.user;

import java.util.Optional;

import com.kigen.car_reservation_api.dtos.user.UserCivilIdentityDTO;
import com.kigen.car_reservation_api.models.EUser;
import com.kigen.car_reservation_api.models.EUserCivilIdentity;

public interface IUserCivilIdentity {
    
    Boolean checkExistsByCivilIdentityValue(String civilIdentityValue);

    EUserCivilIdentity create(EUser user, UserCivilIdentityDTO userCivilIdentityDTO);

    Optional<EUserCivilIdentity> getByCivilIdentityValue(String civilIdentityValue);

    EUserCivilIdentity getByCivilIdentityValue(String civilIdentityValue, Boolean handleException);

    void save(EUserCivilIdentity userCivilIdentity);
}
