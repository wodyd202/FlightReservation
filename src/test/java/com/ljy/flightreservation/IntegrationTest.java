package com.ljy.flightreservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
abstract public class IntegrationTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    protected String obtainsAccessToken(String username, String password) throws Exception {
        return mockMvc.perform(post("/oauth/token")
                        .param("username",username)
                        .param("password",password))
                .andReturn().getResponse().getContentAsString();
    }

    public String convertJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
