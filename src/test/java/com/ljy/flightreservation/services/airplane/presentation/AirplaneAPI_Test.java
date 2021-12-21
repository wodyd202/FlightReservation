package com.ljy.flightreservation.services.airplane.presentation;

import com.ljy.flightreservation.services.airplane.AirplaneFixture;
import com.ljy.flightreservation.services.airplane.application.AirplaneIntegrationTest;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.ljy.flightreservation.services.airplane.AirplaneFixture.aAirplane;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 항공기 API 테스트
 */
public class AirplaneAPI_Test extends AirplaneIntegrationTest {

    @Test
    @DisplayName("해당 코드의 항공기 조회")
    void getAirplaneModel() throws Exception {
        // given
        newAirplane(aAirplane().code(AirplaneCode.of("airplaneCode")));

        // when
        mockMvc.perform(get("/api/airplane/{airplaneCode}", "airplaneCode"))

        .andExpect(status().isOk());
    }
}
