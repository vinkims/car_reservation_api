package com.kigen.car_reservation_api.services.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.user.UserCivilIdentityDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.user.ECivilIdentityType;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.models.user.EUserCivilIdentity;
import com.kigen.car_reservation_api.repositories.user.UserCivilIdentityDAO;
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
        setCivilIdentityType(userCivilIdentity, userCivilIdentityDTO.getCivilIdentityTypeId());

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
