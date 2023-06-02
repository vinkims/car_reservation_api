package com.kigen.car_reservation_api.services.auth;

import com.kigen.car_reservation_api.dtos.auth.AuthDTO;
import com.kigen.car_reservation_api.dtos.auth.SignoutDTO;
import com.kigen.car_reservation_api.models.user.EUser;

public interface IAuth {
    
    String authenticateUser(AuthDTO authDTO);

    EUser getUser(Integer userId);

    Boolean signoutUser(SignoutDTO signoutDTO);
}
