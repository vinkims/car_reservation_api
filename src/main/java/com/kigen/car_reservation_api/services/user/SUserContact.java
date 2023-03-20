package com.kigen.car_reservation_api.services.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.user.UserContactDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.EContactType;
import com.kigen.car_reservation_api.models.EUser;
import com.kigen.car_reservation_api.models.EUserContact;
import com.kigen.car_reservation_api.repositories.UserContactDAO;
import com.kigen.car_reservation_api.services.contact.IContactType;

@Service
public class SUserContact implements IUserContact {

    @Autowired
    private IContactType sContactType;

    @Autowired
    private UserContactDAO userContactDAO;

    @Override
    public Boolean checkExistsByContactValue(String contactValue) {
        return userContactDAO.existsByContactValue(contactValue);
    }

    @Override
    public EUserContact create(EUser user, UserContactDTO userContactDTO) {

        EUserContact userContact = new EUserContact();
        userContact.setContactValue(userContactDTO.getContactValue());
        userContact.setCreatedOn(LocalDateTime.now());
        userContact.setUser(user);
        setContactType(userContact, userContactDTO.getContactTypeId());

        save(userContact);
        return userContact;
    }

    @Override
    public Optional<EUserContact> getByValue(String contactValue) {
        return userContactDAO.findById(contactValue);
    }

    @Override
    public EUserContact getByValue(String contactValue, Boolean handleException) {
        Optional<EUserContact> userContact = getByValue(contactValue);
        if (!userContact.isPresent() && handleException) {
            throw new InvalidInputException("user contact with specified value not found", "userContactValue");
        }
        return userContact.get();
    }

    @Override
    public void save(EUserContact userContact) {
        userContactDAO.save(userContact);
    }
    
    public void setContactType(EUserContact userContact, Integer contactTypeId) {
        if (contactTypeId == null) { return; }

        EContactType contactType = sContactType.getById(contactTypeId, true);
        userContact.setContactType(contactType);
    }
}
