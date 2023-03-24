package com.kigen.car_reservation_api.user;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * The server is not started; layers below are tested.
 * Processed like a real HTTP request without the cost of starting the server.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TestWebApplication {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnMessage() throws Exception {
        this.mockMvc.perform(get("/user"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("successfully fetched users")));
    }
}
