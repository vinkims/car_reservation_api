package com.kigen.car_reservation_api.services.auth;

import com.kigen.car_reservation_api.models.security.EBlacklistToken;
import com.kigen.car_reservation_api.models.user.EUser;

public interface IBlacklist {
    
    Boolean checkExistsByToken(Integer tokenHash);

    EBlacklistToken create(String token, EUser user);

    Integer getTokenHash(String token);

    void save(EBlacklistToken blacklistToken);
}
