package com.ljy.flightreservation.services.reservation.domain;

import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * 예약 도메인 테스트
 */
public class Reservation_Test {

    @Test
    @DisplayName("예약 도메인 생성")
    void newReservation(){
        // when
        Reservation reservation = Reservation.builder()
                .flightInfo(FlightInfo.builder()
                        .seq(1)
                        .departureDate(LocalDate.now())
                        .arrivalDate(LocalDate.now())
                        .departureTime(1)
                        .estimatedArrivalTime(15)
                        .arrivalArea("제주 공항")
                        .build())
                .price(TotalPrice.won(30000))
                .booker(Booker.of("예약자 아이디"))
                .sitInfo(SitInfo.builder()
                        .code("S1")
                        .build())
                .build();
        reservation.reserve(mock(RegisterReservationValidator.class));
        ReservationModel reservationModel = reservation.toModel();

        // then
        assertEquals(reservationModel.getFlightInfo().getSeq(), 1);
        assertEquals(reservationModel.getFlightInfo().getDepartureDate(), LocalDate.now());
        assertEquals(reservationModel.getFlightInfo().getArrivalDate(), LocalDate.now());
        assertEquals(reservationModel.getFlightInfo().getDepartureTime(), 1);
        assertEquals(reservationModel.getFlightInfo().getEstimatedArrivalTime(), 15);
        assertEquals(reservationModel.getFlightInfo().getArrivalArea(), "제주 공항");
        assertEquals(reservationModel.getPrice(), 30000);
        assertEquals(reservationModel.getBooker(), "예약자 아이디");
        assertEquals(reservationModel.getReservationDate(), LocalDate.now());
        assertEquals(reservationModel.getSitCode(), "S1");
    }
}
