package com.kigen.car_reservation_api.services.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.user.UserCivilIdentityDTO;
import com.kigen.car_reservation_api.dtos.user.UserContactDTO;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.models.user.ERole;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.models.user.EUserCivilIdentity;
import com.kigen.car_reservation_api.models.user.EUserContact;
import com.kigen.car_reservation_api.repositories.user.UserDAO;
import com.kigen.car_reservation_api.services.role.IRole;
import com.kigen.car_reservation_api.services.status.IStatus;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SUser implements IUser {

    Logger logger = LoggerFactory.getLogger(SUser.class);

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IRole sRole;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUserCivilIdentity sUserCivilIdentity;

    @Autowired
    private IUserContact sUserContact;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private UserDAO userDAO;

    @Override
    public EUser create(UserDTO userDTO) {

        EUser user = new EUser();
        user.setAge(userDTO.getAge());
        user.setCreatedOn(LocalDateTime.now());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setPasscode(userDTO.getPasscode());
        setRole(user, userDTO.getRoleId());
        
        Integer statusId = userDTO.getStatusId() == null ? activeStatusId : userDTO.getStatusId();
        setStatus(user, statusId);

        save(user);

        setCivilIdentitesData(user, userDTO.getCivilIdentities());
        setContactData(user, userDTO.getContacts());
        
        return user;
    }

    @Override
    public List<EUser> getAll() {
        return userDAO.findAll();
    }

    @Override
    public Optional<EUser> getByContactValue(String contactValue) {
        return userDAO.findByContactValue(contactValue);
    }

    @Override
    public Optional<EUser> getById(Integer userId) {
        return userDAO.findById(userId);
    }

    @Override
    public EUser getById(Integer userId, Boolean handleException) {
        Optional<EUser> user = getById(userId);
        if (!user.isPresent() && handleException) {
            throw new InvalidInputException("user with specified id not found", "userId");
        }
        return user.get();
    }

    @Override
    public Optional<EUser> getByIdOrContactValue(String userValue) {
        Integer userId;
        try {
            userId = Integer.valueOf(userValue);
        } catch (NumberFormatException e) {
            userId = (Integer) null;
            logger.info("ERROR: [SUser.getByIdOrContactValue] |\n[MSG] {}", e.getMessage());
            return getByContactValue(userValue);
        }

        return userDAO.findByIdOrContactValue(userId, userValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EUser> specBuilder = new SpecBuilder<EUser>();

        specBuilder = (SpecBuilder<EUser>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<EUser> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return userDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EUser user) {
        userDAO.save(user);
    }

    public void setCivilIdentitesData(EUser user, List<UserCivilIdentityDTO> civilIdentitiesList) {
        if (civilIdentitiesList == null || civilIdentitiesList.isEmpty()) { return; }

        List<EUserCivilIdentity> userCivilIdentities = new ArrayList<>();
        for (UserCivilIdentityDTO userCivilIdentityDTO : civilIdentitiesList) {
            EUserCivilIdentity userCivilIdentity = sUserCivilIdentity.create(user, userCivilIdentityDTO);
            userCivilIdentities.add(userCivilIdentity);
        }
        user.setCivilIdentities(userCivilIdentities);
    }

    public void setContactData(EUser user, List<UserContactDTO> contactList) {
        if (contactList == null || contactList.isEmpty()) { return; }

        List<EUserContact> userContacts = new ArrayList<>();
        for (UserContactDTO userContactDTO : contactList) {
            EUserContact contact = sUserContact.create(user, userContactDTO);
            userContacts.add(contact);
        }
        user.setContacts(userContacts);
    }

    public void setRole(EUser user, Integer roleId) {
        if (roleId == null) { return; }

        ERole role = sRole.getById(roleId, true);
        user.setRole(role);
    }

    public void setStatus(EUser user, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        user.setStatus(status);
    }

    @Override
    public EUser update(EUser user, UserDTO userDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        String[] fields = {"Age", "FirstName", "MiddleName", "LastName", "Passcode"};
        for (String field : fields) {
            Method getField = UserDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(userDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EUser.class.getMethod("set" + field, fieldValue.getClass()).invoke(user, fieldValue);
            }
        }

        user.setModifiedOn(LocalDateTime.now());
        setRole(user, userDTO.getRoleId());
        setStatus(user, userDTO.getStatusId());

        save(user);

        setCivilIdentitesData(user, userDTO.getCivilIdentities());
        setContactData(user, userDTO.getContacts());

        return user;
    }
    
}
