package com.ljy.flightreservation.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.flightreservation.ApiTest;
import com.ljy.flightreservation.user.command.application.model.RegisterUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class UserAPITest extends ApiTest {

    @Test
    void register() throws Exception{
        RegisterUser userCommand = RegisterUser.builder()
                .id("test123")
                .password("passowrd")
                .email("test@test.com")
                .build();

        mvc.perform(post("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userCommand)))
                    .andDo(print())
                    .andExpect(status().isOk());
    }
}
