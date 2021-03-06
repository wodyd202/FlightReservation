package com.ljy.flightreservation.restdocs.reservation;

import com.ljy.flightreservation.services.airplane.domain.value.*;
import com.ljy.flightreservation.services.flight.domain.value.AirplaneInfo;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Money;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
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

public class ReservationAPI_Test extends ReservationRestDocsTest {

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
    @DisplayName("??????")
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
        .andDo(document("reserve",
        requestHeaders(
                headerWithName("X-AUTH-TOKEN").description("jwt ??????")
        ),
        requestFields(
                fieldWithPath("flightInfoSeq").type(NUMBER).description("?????? ?????? ?????? ??????"),
                fieldWithPath("sitCode").type(STRING).description("?????? ??????")
        )
        ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????")
    void getReservationModel() throws Exception {
        // given
        ReservationModel reservationModel = newReservation(aReservation().booker(Booker.of("reservemember")));

        mockMvc.perform(get("/api/reservation/{reservationSeq}", reservationModel.getSeq())
                .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password")
        ))

        // then
        .andDo(document("get_reservation",
        pathParameters(
                parameterWithName("reservationSeq").description("?????? ?????? ??????")
        ),
        responseFields(
                fieldWithPath("seq").type(NUMBER).description("?????? ?????? ??????"),
                fieldWithPath("flightInfo").type(OBJECT).description("?????? ?????? ??????"),
                fieldWithPath("flightInfo.seq").type(NUMBER).description("?????? ?????? ?????? ??????"),
                fieldWithPath("flightInfo.departureDate").type(STRING).description("?????? ??????"),
                fieldWithPath("flightInfo.departureTime").type(NUMBER).description("?????? ??????"),
                fieldWithPath("flightInfo.arrivalDate").type(STRING).description("?????? ??????"),
                fieldWithPath("flightInfo.estimatedArrivalTime").type(NUMBER).description("?????? ?????? ??????"),
                fieldWithPath("flightInfo.arrivalArea").type(STRING).description("?????? ??????"),
                fieldWithPath("price").type(NUMBER).description("?????? ??????"),
                fieldWithPath("state").type(STRING).description("??????"),
                fieldWithPath("reservationDate").type(STRING).description("?????? ??????"),
                fieldWithPath("sitCode").type(STRING).description("?????? ??????"),
                fieldWithPath("booker").type(STRING).description("?????????")
        )));
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ????????? ????????? ????????????")
    void getReservationByFlight() throws Exception {
        // given
        ReservationModel reservationModel = newReservation(aReservation().booker(Booker.of("reservemember")));

        // when
        mockMvc.perform(get("/api/reservation/flight/{flightSeq}",reservationModel.getFlightInfo().getSeq())
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                )

        // then
        .andDo(document("get_reservation_by_flight_seq",
        pathParameters(
                parameterWithName("flightSeq").description("?????? ?????? ?????? ??????")
        ),
        responseFields(
                fieldWithPath("reservations").type(ARRAY).description("?????? ?????????"),
                fieldWithPath("reservations[].seq").ignored(),
                fieldWithPath("reservations[].flightInfo").ignored(),
                fieldWithPath("reservations[].price").ignored(),
                fieldWithPath("reservations[].reservationDate").ignored(),
                fieldWithPath("reservations[].booker").ignored(),
                fieldWithPath("reservations[].state").ignored(),
                fieldWithPath("reservations[].sitCode").type(STRING).description("?????? ??????"),
                fieldWithPath("totalElement").type(NUMBER).description("?????? ??? ??????")
        )));
    }

    @Test
    @DisplayName("?????? ?????? ????????????(?????? ???????????? ??????)")
    void getReservationModels() throws Exception {
        // when
        mockMvc.perform(get("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .param("page", "0")
                )

        // then
        .andDo(document("get_reservation_list",
        requestParameters(
                parameterWithName("page").description("????????? ??????")
        )));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ????????????")
    void getPastReservationModels() throws Exception {
        // when
        mockMvc.perform(get("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .param("page", "0")
                        .param("past", "true")
                )

        // then
        .andDo(document("get_reservation_list_by_past",
        requestParameters(
                parameterWithName("page").description("????????? ??????"),
                parameterWithName("past").description("?????? ?????? ?????? ??????")
        )));
    }

    @Test
    @DisplayName("?????? ???????????? ??? ?????? ?????? ????????????")
    void getReservationModelsByArea() throws Exception {
        // when
        mockMvc.perform(get("/api/reservation")
                        .header("X-AUTH-TOKEN",obtainsAccessToken("reservemember","password"))
                        .param("page", "0")
                        .param("arrivalArea", "?????? ??????")
                )

        // then
        .andDo(document("get_reservation_list_by_area",
        requestParameters(
                parameterWithName("page").description("????????? ??????"),
                parameterWithName("arrivalArea").description("?????? ??????")
        ),
        responseFields(
                fieldWithPath("reservations").type(ARRAY).description("?????? ?????????"),
                fieldWithPath("reservations[].seq").type(NUMBER).description("?????? ?????? ??????"),
                fieldWithPath("reservations[].flightInfo").type(OBJECT).description("?????? ?????? ??????"),
                fieldWithPath("reservations[].flightInfo.seq").type(NUMBER).description("?????? ?????? ?????? ??????"),
                fieldWithPath("reservations[].flightInfo.departureDate").type(STRING).description("?????? ??????"),
                fieldWithPath("reservations[].flightInfo.departureTime").type(NUMBER).description("?????? ??????"),
                fieldWithPath("reservations[].flightInfo.arrivalDate").type(STRING).description("?????? ??????"),
                fieldWithPath("reservations[].flightInfo.estimatedArrivalTime").type(NUMBER).description("?????? ?????? ??????"),
                fieldWithPath("reservations[].flightInfo.arrivalArea").type(STRING).description("?????? ??????"),
                fieldWithPath("reservations[].price").type(NUMBER).description("?????? ??????"),
                fieldWithPath("reservations[].reservationDate").type(STRING).description("?????? ??????"),
                fieldWithPath("reservations[].booker").type(STRING).description("?????????"),
                fieldWithPath("reservations[].state").ignored(),
                fieldWithPath("reservations[].sitCode").ignored(),
                fieldWithPath("totalElement").type(NUMBER).description("?????? ??? ??????")
        )));
    }
}
