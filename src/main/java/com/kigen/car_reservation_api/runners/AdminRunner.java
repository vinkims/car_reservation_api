package com.kigen.car_reservation_api.runners;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kigen.car_reservation_api.dtos.user.UserContactDTO;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.services.user.IUser;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminRunner implements CommandLineRunner {

    @Value(value = "${default.value.user.admin-email}")
    private String adminEmail;

    @Value(value = "${default.value.user.admin-password}")
    private String adminPassword;

    @Value(value = "${default.value.contact.email-type-id}")
    private Integer emailContactTypeId;

    @Value(value = "${default.value.role.system-admin-id}")
    private Integer systemAdminRoleId;

    @Autowired
    private IUser sUser;

    @Override
    public void run(String... args) throws Exception {

        String firstName = "system";
        String lastName = "admin";

        log.info("\n>>> check if admin user exists");
        Optional<EUser> user = sUser.getByContactValue(adminEmail);

        if (user.isPresent()) {
            log.info("\n<<< Admin user exists");
            return;
        }

        log.info("\n<<< Creating admin user");
        
        UserContactDTO userContactDTO = new UserContactDTO();
        userContactDTO.setContactTypeId(emailContactTypeId);
        userContactDTO.setContactValue(adminEmail);
        List<UserContactDTO> contacts = new ArrayList<>();
        contacts.add(userContactDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setContacts(contacts);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPasscode(adminPassword);
        userDTO.setRoleId(systemAdminRoleId);

        EUser admin = sUser.create(userDTO);

        log.info("\nSysAdmin created: id=>{}, name=>{}, email=>{}",
            admin.getId(), String.format("%s %s", firstName,lastName), adminEmail);
    }
    
}
