package com.kigen.car_reservation_api.services.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.user.UserCivilIdentityDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.ECivilIdentityType;
import com.kigen.car_reservation_api.models.EUser;
import com.kigen.car_reservation_api.models.EUserCivilIdentity;
import com.kigen.car_reservation_api.repositories.UserCivilIdentityDAO;
import com.kigen.car_reservation_api.services.civilIdentity.ICivilIdentityType;

@Service
public class SUserCivilIdentity implements IUserCivilIdentity {

    @Autowired
    private ICivilIdentityType sCivilIdentityType;

    @Autowired
    private UserCivilIdentityDAO userCivilIdentityDAO;

    @Override
    public Boolean checkExistsByCivilIdentityValue(String civilIdentityValue) {
        return userCivilIdentityDAO.existsByCivilIdentityValue(civilIdentityValue);
    }

    @Override
    public EUserCivilIdentity create(EUser user, UserCivilIdentityDTO userCivilIdentityDTO) {

        EUserCivilIdentity userCivilIdentity = new EUserCivilIdentity();
        userCivilIdentity.setCivilIdentityValue(userCivilIdentityDTO.getCivilIdentityValue());
        userCivilIdentity.setCreatedOn(LocalDateTime.now());
        userCivilIdentity.setUser(user);

        save(userCivilIdentity);
        return userCivilIdentity;
    }

    @Override
    public Optional<EUserCivilIdentity> getByCivilIdentityValue(String civilIdentityValue) {
        return userCivilIdentityDAO.findById(civilIdentityValue);
    }

    @Override
    public EUserCivilIdentity getByCivilIdentityValue(String civilIdentityValue, Boolean handleException) {
        Optional<EUserCivilIdentity> userCivilIdentity = getByCivilIdentityValue(civilIdentityValue);
        if (!userCivilIdentity.isPresent() && handleException) {
            throw new InvalidInputException("user civil identity with specified value not found", "userCivilIdentityValue");
        }
        return userCivilIdentity.get();
    }

    @Override
    public void save(EUserCivilIdentity userCivilIdentity) {
        userCivilIdentityDAO.save(userCivilIdentity);
    }

    public void setCivilIdentityType(EUserCivilIdentity userCivilIdentity, Integer civilIdentityTypeId) {
        if (civilIdentityTypeId == null) { return; }

        ECivilIdentityType civilIdentityType = sCivilIdentityType.getById(civilIdentityTypeId, true);
        userCivilIdentity.setCivilIdentityType(civilIdentityType);
    }
    
}
