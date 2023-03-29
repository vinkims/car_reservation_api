package com.kigen.car_reservation_api.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.repositories.user.UserDAO;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryUnitTest {
    
    @Autowired
    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        EUser user1 = new EUser();
        user1.setId(101);
        user1.setAge(23);
        user1.setFirstName("Kipkurgat");
        user1.setLastName("Kigen");
        userDAO.save(user1);

        EUser user2 = new EUser();
        user2.setId(110);
        user2.setAge(29);
        user2.setFirstName("Kimutai");
        user2.setLastName("Kirui");
        userDAO.save(user2);
    }

    @AfterEach
    public void destroy() {
        userDAO.deleteAll();
    }

    @Test
    public void testGetAllUsers() {
        List<EUser> usersList = userDAO.findAll();
        Assertions.assertThat(usersList.size()).isEqualTo(2);
        Assertions.assertThat(usersList.get(0).getId()).isNotNegative();
        Assertions.assertThat(usersList.get(0).getId()).isGreaterThan(0);
        Assertions.assertThat(usersList.get(0).getFirstName()).isEqualTo("Kipkurgat");
    }

    @Test
    public void testGetInvalidUser() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userDAO.findById(100).get();
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No value present");
    }

    @Test
    public void testGetCreateOrder() {
        EUser user = new EUser();
        user.setId(110);
        user.setAge(23);
        user.setFirstName("Kimeli");
        user.setLastName("Kemei");
        
        EUser returnedUser = userDAO.save(user);

        Assertions.assertThat(returnedUser).isNotNull();
        Assertions.assertThat(returnedUser.getFirstName()).isNotEmpty();
        Assertions.assertThat(returnedUser.getId()).isGreaterThan(1);
        Assertions.assertThat(returnedUser.getId()).isNotNegative();
        Assertions.assertThat(user.getFirstName()).isEqualTo(returnedUser.getFirstName());
    }
}
