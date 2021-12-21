
package com.ljy.flightreservation.restdocs.flight;

import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.ljy.flightreservation.services.flight.FlightFixtrue.aFlight;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 운항 정보 API 테스트
 */
public class FlightAPI_Test extends FlightRestDocsTest {

    @Test
    @DisplayName("운항 상세 정보 조회")
    void getFlightModel() throws Exception {
        // given
        FlightModel flightModel = newFlight(aFlight());

        // when
        mockMvc.perform(get("/api/flight/{flightSeq}", flightModel.getSeq()))

        // then
        .andDo(document("get_flight",
        pathParameters(
                parameterWithName("flightSeq").description("운항 정보 고유 번호")
        ),
        responseFields(
                fieldWithPath("seq").type(NUMBER).description("운항 정보 고유 번호"),
                fieldWithPath("basePrice").type(NUMBER).description("기본 금액"),
                fieldWithPath("airplaneInfo").type(OBJECT).description("항공기 정보"),
                fieldWithPath("airplaneInfo.code").type(STRING).description("항공기 코드"),
                fieldWithPath("flightDetail").type(OBJECT).description("운항 상세 정보"),
                fieldWithPath("flightDetail.departureDate").type(STRING).description("출발 일자"),
                fieldWithPath("flightDetail.arrivalDate").type(STRING).description("도착 일자"),
                fieldWithPath("flightDetail.departureTime").type(NUMBER).description("출발 시각"),
                fieldWithPath("flightDetail.estimatedArrivalTime").type(NUMBER).description("도착 예정 시각"),
                fieldWithPath("flightDetail.arrivalArea").type(STRING).description("도착 지역"),
                fieldWithPath("needPassport").type(STRING).description("여권 필요 여부"),
                fieldWithPath("state").type(STRING).description("운항 정보 상태")
        )));
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
        .andDo(document("get_flight_list_by_area",
        requestParameters(
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("arrivalArea").description("도착 지역")
        )));
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
        .andDo(document("get_flight_list_by_departure_date",
        requestParameters(
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("departureDate").description("출발 일자")
        )));
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
        .andDo(document("get_flight_list_by_depareture_date_and_arrival_date",
        requestParameters(
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("departureDate").description("출발 일자"),
                parameterWithName("arrivalDate").description("도착 일자")
        )));
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
        .andDo(document("get_flight_list_by_arrival_date",
        requestParameters(
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("arrivalDate").description("도착 일자")
        )));
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
        .andDo(document("get_flight_list",
        requestParameters(
                parameterWithName("page").description("페이지 번호")
        ),
        responseFields(
                fieldWithPath("flights").type(ARRAY).description("운항 정보"),
                fieldWithPath("totalElement").type(NUMBER).description("운항 정보 총 개수"),
                fieldWithPath("flights[].seq").type(NUMBER).description("운항 정보 고유 번호"),
                fieldWithPath("flights[].basePrice").type(NUMBER).description("기본 금액"),
                fieldWithPath("flights[].airplaneInfo").ignored(),
                fieldWithPath("flights[].flightDetail").type(OBJECT).description("운항 상세 정보"),
                fieldWithPath("flights[].flightDetail.departureDate").type(STRING).description("출발 일자"),
                fieldWithPath("flights[].flightDetail.arrivalDate").type(STRING).description("도착 일자"),
                fieldWithPath("flights[].flightDetail.departureTime").type(NUMBER).description("출발 시각"),
                fieldWithPath("flights[].flightDetail.estimatedArrivalTime").type(NUMBER).description("도착 예정 시각"),
                fieldWithPath("flights[].flightDetail.arrivalArea").type(STRING).description("도착 지역"),
                fieldWithPath("flights[].needPassport").type(STRING).description("여권 필요 여부"),
                fieldWithPath("flights[].state").type(STRING).description("운항 정보 상태")
        )));
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
        .andDo(document("get_flight_list_by_past",
        requestParameters(
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("past").description("지난 운항 정보 조회 여부")
        )));
    }
}
