package com.kigen.car_reservation_api.user;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kigen.car_reservation_api.controllers.user.CUser;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.services.user.SUser;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CUser.class)
public class UserControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SUser sUser;

    private EUser user;

    private UserDTO userDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        user = new EUser();
        user.setId(112);
        user.setAge(29);
        user.setFirstName("Kiptoo");
        user.setLastName("Bungei");

        userDTO = new UserDTO();
        userDTO.setAge(29);
        userDTO.setFirstName("Kiptoo");
        userDTO.setLastName("Bungei");
    }

    @Test
    public void testGetUsers() throws Exception {
        when(sUser.getAll()).thenReturn(Collections.singletonList(user));
        mockMvc.perform(get("/user"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserById() throws Exception {
        when(sUser.getById(112).get()).thenReturn(user);
        mockMvc.perform(get("/user/112"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is("Kiptoo")))
            .andExpect(jsonPath("$.id", is(112)))
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testCreateUser() throws Exception {
        when(sUser.create(userDTO)).thenReturn(user);
        mockMvc.perform(
            post("/user")
                .content(objectMapper.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName", is("Kiptoo")))
            .andExpect(jsonPath("$.age", is(29)))
            .andExpect(jsonPath("$").isNotEmpty());
    }
}
