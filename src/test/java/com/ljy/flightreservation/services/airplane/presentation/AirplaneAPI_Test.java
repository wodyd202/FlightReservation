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

        .andExpect(status().isOk())
        .andDo(document("get_airplane",
                pathParameters(
                        parameterWithName("airplaneCode").description("항공기 코드")
                ),
                responseFields(
                        fieldWithPath("code").type(STRING).description("항공기 코드"),
                        fieldWithPath("sitInfo").type(OBJECT).description("항공기 좌석 정보"),
                        fieldWithPath("sitInfo.sitCodeInfo").type(OBJECT).description("항공기 좌석 코드 정보"),
                        fieldWithPath("sitInfo.sitCodeInfo.lastRowSitCode").type(STRING).description("좌석 끝 가로 좌표(A-Z)"),
                        fieldWithPath("sitInfo.sitCodeInfo.lastColSitNumber").type(NUMBER).description("좌석 끝 세로 좌표(0-9)"),
                        fieldWithPath("sitInfo.specialSitInfo").type(OBJECT).description("스페셜 좌석 정보"),
                        fieldWithPath("sitInfo.specialSitInfo.sitList").type(ARRAY).description("스페셜 좌석 좌표 리스트"),
                        fieldWithPath("sitInfo.specialSitInfo.sitSurcharge").type(NUMBER).description("스페셜 좌석 추가 요금"),
                        fieldWithPath("sitInfo.businessSitInfo").type(OBJECT).description("비즈니스 좌석 정보"),
                        fieldWithPath("sitInfo.businessSitInfo.sitList").type(ARRAY).description("비즈니스 좌석 좌표 리스트"),
                        fieldWithPath("sitInfo.businessSitInfo.sitSurcharge").type(NUMBER).description("비즈니스 좌석 추가 요금"),
                        fieldWithPath("state").type(STRING).description("항공기 상태")
                )));
    }
}
