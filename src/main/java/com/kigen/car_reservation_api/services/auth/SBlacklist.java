package com.kigen.car_reservation_api.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.models.security.EBlacklistToken;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.repositories.auth.BlacklistTokenDAO;

@Service
public class SBlacklist implements IBlacklist {

    @Autowired
    private BlacklistTokenDAO blacklistTokenDAO;

    @Override
    public Boolean checkExistsByToken(Integer tokenHash) {
        return blacklistTokenDAO.existsByTokenHash(tokenHash);
    }

    @Override
    public EBlacklistToken create(String token, EUser user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Integer getTokenHash(String token) {
        return token.hashCode();
    }

    @Override
    public void save(EBlacklistToken blacklistToken) {
        blacklistTokenDAO.save(blacklistToken);
    }
    
}
