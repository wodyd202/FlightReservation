package com.ljy.flightreservation.services.reservation;

import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;

import java.time.LocalDate;

public class ReservationFixture {
    public static Reservation.ReservationBuilder aReservation() {
        return Reservation.builder()
                .price(TotalPrice.won(100_000))
                .flightInfo(FlightInfo.builder()
                        .basePrice(BasePrice.won(100_000))
                        .arrivalArea("제주 공항")
                        .estimatedArrivalTime(20)
                        .departureTime(10)
                        .arrivalDate(LocalDate.now())
                        .departureDate(LocalDate.now())
                        .build())
                .sitInfo(SitInfo.builder()
                        .code("S1")
                        .build())
                .booker(Booker.of("booker"));
    }
}
