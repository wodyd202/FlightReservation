
package com.ljy.flightreservation.services.flight.presentation;

import com.ljy.flightreservation.services.flight.FlightIntegrationTest;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.ljy.flightreservation.services.flight.FlightFixtrue.aFlight;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 운항 정보 API 테스트
 */
public class FlightAPI_Test extends FlightIntegrationTest {

    @Test
    @DisplayName("운항 상세 정보 조회")
    void getFlightModel() throws Exception {
        // given
        FlightModel flightModel = newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight/{flightSeq}", flightModel.getSeq()))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 지역으로 가는 운항 정보 리스트 조회")
    void getFlightModelsToArea() throws Exception {
        // given
        newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight")
                        .param("page", "0")
                        .param("arrivalArea", "제주 공항"))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 날짜에 출발하는 운항 정보 리스트 조회")
    void getFlightModelsToDepartureDate() throws Exception {
        // given
        newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight")
                        .param("page", "0")
                        .param("departureDate", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 날짜에 출발해서 특정 날짜에 도착하는 운항 정보 리스트 조회")
    void getFlightModelsToDepartureDateAndArrivalDate() throws Exception{
        // given
        newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight")
                        .param("page", "0")
                        .param("departureDate", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .param("arrivalDate", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 날짜에 도착하는 운항 정보 리스트 조회")
    void getFlightModelsToArrivalDate() throws Exception{
        // given
        newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight")
                        .param("page", "0")
                        .param("arrivalDate", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))

        // then
        .andExpect(status().isOk());
    }


    @Test
    @DisplayName("운항 정보 리스트 조회")
    void getFlightModels() throws Exception {
        // given
        newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight")
                        .param("page", "0"))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("지난 운항 정보 리스트 조회")
    void getPastFlightModels() throws Exception{
        // given
        newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight")
                .param("page", "0")
                .param("past", "true"))

        // then
        .andExpect(status().isOk());
    }
}
