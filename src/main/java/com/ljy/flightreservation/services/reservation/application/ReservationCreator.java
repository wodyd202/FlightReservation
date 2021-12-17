package com.ljy.flightreservation.services.reservation.application;

import com.ljy.flightreservation.services.flight.domain.exception.FlightNotFoundException;
import com.ljy.flightreservation.services.reservation.application.external.AirplaneRepository;
import com.ljy.flightreservation.services.reservation.application.external.AirplaneSitInfo;
import com.ljy.flightreservation.services.reservation.application.external.FlightRepository;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationCreator {
    // external
    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;

    public Reservation create(Reservate reservate, Booker booker) {
        FlightInfo flightInfo = flightRepository.getFlightInfo(reservate.getFlightInfoSeq()).orElseThrow(FlightNotFoundException::new);
        AirplaneSitInfo airplaneSitInfo = airplaneRepository.getAirplaneSitInfo(flightInfo.getAirplaneCode());

        return Reservation.builder()
                .price(airplaneSitInfo.calcaulteTotalPrice(flightInfo, reservate.getSitCode()))
                .sitInfo(SitInfo.builder()
                        .code(reservate.getSitCode())
                        .build())
                .flightInfo(flightInfo)
                .booker(booker)
                .build();
    }
}
