package com.kigen.car_reservation_api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.kigen.car_reservation_api.services.user.SUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.repositories.user.UserDAO;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    private SUser sUser;

    @Mock
    UserDAO userDAO;

    @Test
    public void testGetUsersList() {
        EUser user1 = new EUser();
        user1.setId(23);
        user1.setFirstName("Kipkulei");
        user1.setLastName("Kigen");;

        EUser user2 = new EUser();
        user2.setId(24);
        user2.setFirstName("Kiptoo");
        user2.setLastName("Kirui");

        when(userDAO.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<EUser> usersList = sUser.getAll();
        assertEquals(usersList.size(), 2);
        assertEquals(usersList.get(0).getFirstName(), "Kipkulei");
        assertEquals(usersList.get(1).getFirstName(), "Kiptoo");
    }
    
    @Test
    public void testGetUserById() {
        EUser user = new EUser();
        user.setId(23);
        user.setFirstName("Kipkulei");
        user.setLastName("Kigen");
        when(userDAO.findById(23)).thenReturn(Optional.of(user));
        EUser userById = sUser.getById(23).get();
        assertEquals(userById.getFirstName(), "Kipkulei");
        assertEquals(userById.getLastName(), "Kigen");
    }

    @Test
    public void testGetInvalidUserById() {
        when(userDAO.findById(43)).thenThrow(
            new InvalidInputException("user with specified id not found", "userId")
        );
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            sUser.getById(43, true);
        });
        assertTrue(exception.getMessage().contains("user with specified id not found"));
    }

    @Test
    public void testCreateUser() {   
        UserDTO userDTO = new UserDTO();
        userDTO.setAge(22);
        userDTO.setFirstName("Kipkurgat");
        userDTO.setLastName("Ronoh");
        EUser user = sUser.create(userDTO);
        verify(userDAO, times(1)).save(user);

        ArgumentCaptor<EUser> userArgumentCaptor = ArgumentCaptor.forClass(EUser.class);
        verify(userDAO).save(userArgumentCaptor.capture());

        EUser userCreated = userArgumentCaptor.getValue();
        assertNotNull(userCreated.getAge());
        assertEquals("Ronoh", userCreated.getLastName());
    }
}
