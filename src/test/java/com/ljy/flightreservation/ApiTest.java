package com.ljy.flightreservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {
    @Autowired protected MockMvc mvc;

    protected String obtainJwtToken(String username, String password) throws Exception{
        MockHttpServletResponse response = mvc.perform(post("/oauth/token")
                .param("username", "test123")
                .param("password", "password"))
                .andReturn().getResponse();
        return response.getContentAsString();
    }
}
