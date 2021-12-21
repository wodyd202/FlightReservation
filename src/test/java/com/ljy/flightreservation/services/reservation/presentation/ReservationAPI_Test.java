package com.ljy.flightreservation.services.reservation.presentation;

import com.ljy.flightreservation.services.airplane.domain.value.*;
import com.ljy.flightreservation.services.flight.domain.value.AirplaneInfo;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Money;
import com.ljy.flightreservation.services.reservation.ReservationFixture;
import com.ljy.flightreservation.services.reservation.ReservationIntegrationTest;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.ljy.flightreservation.services.airplane.AirplaneFixture.aAirplane;
import static com.ljy.flightreservation.services.flight.FlightFixtrue.aFlight;
import static com.ljy.flightreservation.services.member.MemberFixture.aMember;
import static com.ljy.flightreservation.services.reservation.ReservationFixture.aReservation;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationAPI_Test extends ReservationIntegrationTest {

    long flightInfoSeq;

    @BeforeEach
    void setUp(){
        newMember(aMember().id(MemberId.of("reservemember")), Money.won(3_000_000));
        newAirplane(aAirplane()
                .code(AirplaneCode.of("airplaneCode_4"))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C3"))
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(Arrays.asList("S1","B1"))
                                .sitSurcharge(30_000)
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(Arrays.asList("C1", "C2"))
                                .sitSurcharge(50_000)
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(7)
                                .lastRowSitCode("Z")
                                .build())
                        .build()));
        flightInfoSeq = newFlight(aFlight()
                        .airplaneInfo(AirplaneInfo.builder()
                        .code("airplaneCode_4")
                        .build())
                .basePrice(BasePrice.won(100_000)));
        newReservation(aReservation().booker(Booker.of("reservemember")));
    }

    @Test
    @DisplayName("예약시 토큰을 헤더에 포함시켜야함")
    void reserve_403() throws Exception {
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("C1")
                .build();

        // when
        mockMvc.perform(post("/api/reservation"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("예약")
    void reserve() throws Exception {
        // given
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("C1")
                .build();

        // when
        mockMvc.perform(post("/api/reservation")
                .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJson(reservate)))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("예약시 좌석 코드를 입력하지 않으면 400 에러")
    void emptySitCode() throws Exception {
        // given
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("")
                .build();

        // when
        mockMvc.perform(post("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJson(reservate)))

        // then
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("예약 상세 정보 조회")
    void getReservationModel() throws Exception {
        // given
        ReservationModel reservationModel = newReservation(aReservation().booker(Booker.of("reservemember")));

        mockMvc.perform(get("/api/reservation/{reservationSeq}", reservationModel.getSeq())
                .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password")
        ))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("해당 운항 정보에 대한 예약된 좌석들 가져오기")
    void getReservationByFlight() throws Exception {
        // given
        ReservationModel reservationModel = newReservation(aReservation().booker(Booker.of("reservemember")));

        // when
        mockMvc.perform(get("/api/reservation/flight/{flightSeq}",reservationModel.getFlightInfo().getSeq())
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                )

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("예약 목록 가져오기(아직 출발하지 않은)")
    void getReservationModels() throws Exception {
        // when
        mockMvc.perform(get("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .param("page", "0")
                )

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("지난 예약 목록 가져오기")
    void getPastReservationModels() throws Exception {
        // when
        mockMvc.perform(get("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .param("page", "0")
                        .param("past", "true")
                )

        // then
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 지역으로 간 예약 목록 가져오기")
    void getReservationModelsByArea() throws Exception {
        // when
        mockMvc.perform(get("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .param("page", "0")
                        .param("arrivalArea", "제주 공항")
                )

        // then
        .andExpect(status().isOk());
    }
}
