package com.kigen.car_reservation_api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kigen.car_reservation_api.models.user.ERole;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.repositories.user.UserDAO;
import com.kigen.car_reservation_api.services.role.IRole;
import com.kigen.car_reservation_api.services.user.IUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiIntegrationTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IUser sUser;

    @Autowired
    private IRole sRole;

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    // @Test
    @Sql(statements = "INSERT INTO users(id, age, first_name, last_name, role_id, status_id) VALUES (101, 30, 'Kipkurgat', 'Mutai', 3, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id='101'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetUsersList() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<EUser>> response = restTemplate.exchange(
            createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<EUser>>(){}
        );
        List<EUser> userList = response.getBody();
        assert userList != null;
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(userList.size(), sUser.getAll().size());
        assertEquals(userList.size(), userDAO.findAll().size());
    }

    // @Test
    @Sql(statements = "INSERT INTO users(id, age, first_name, last_name, role_id, status_id) VALUES (110, 29, 'Kimutai', 'Kigen', 2, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM users WHERE id='110'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetUserById() throws JsonProcessingException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<EUser> response = restTemplate.exchange(
            (createURLWithPort() + "/110"), HttpMethod.GET, entity, EUser.class
        );
        EUser userRes = response.getBody();
        String expected = "{\"id\":110,\"age\":29,\"firstName\":\"Kimutai\",\"lastName\":\"Kigen\"}";
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(expected, objectMapper.writeValueAsString(userRes));
        assert userRes != null;
        assertEquals(userRes, sUser.getById(110).get());
        assertEquals(userRes.getFirstName(), sUser.getById(110).get().getFirstName());
        assertEquals(userRes, userDAO.findById(110).orElse(null));
    }

    // @Test
    @Sql(statements = "DELETE FROM users WHERE first_name='Kiptoo' AND last_name='Mutai'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreateUser() throws JsonProcessingException {
        EUser user = new EUser();
        user.setId(11);
        user.setAge(32);
        user.setFirstName("Kiptoo");
        user.setLastName("Mutai");
        ERole role = sRole.getById(3, true);
        user.setRole(role);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
        ResponseEntity<EUser> response = restTemplate.exchange(
            createURLWithPort(), HttpMethod.POST, entity, EUser.class
        );
        assertEquals(response.getStatusCodeValue(), 201);
        EUser userRes = Objects.requireNonNull(response.getBody());
        assertEquals(userRes.getFirstName(), "Kiptoo");
        assertEquals(userRes.getFirstName(), userDAO.save(user).getFirstName());
    }

    public String createURLWithPort() {
        return "http://localhost:" + port + "/api/v1/user";
    }
}
