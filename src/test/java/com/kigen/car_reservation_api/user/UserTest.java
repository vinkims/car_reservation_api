package com.kigen.car_reservation_api.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kigen.car_reservation_api.controllers.user.CUser;

@SpringBootTest
public class UserTest {
    
    @Autowired
    private CUser cUser;

    @Test
    public void contextLoads() throws Exception {
        assertThat(cUser).isNotNull();
    }
}
