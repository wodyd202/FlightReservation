package com.ljy.flightreservation.services.flight.application;

import com.ljy.flightreservation.services.flight.application.model.RegisterFlight;
import com.ljy.flightreservation.services.flight.domain.AirplaneInfo;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightDetail;
import com.ljy.flightreservation.services.flight.domain.FlightState;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FlightMapper {

    public Flight mapFrom(RegisterFlight registerFlight) {
        return Flight.builder()
                .basePrice(BasePrice.won(registerFlight.getBasePrice()))
                .airplaneInfo(AirplaneInfo.builder()
                        .code(registerFlight.getAirplaneCode())
                        .build())
                .flightDetail(FlightDetail.builder()
                        .arrivalDate(registerFlight.getFlightDetail().getArrivalDate())
                        .estimatedArrivalTime(registerFlight.getFlightDetail().getEstimatedArrivalTime())
                        .arrivalArea(registerFlight.getFlightDetail().getArrivalArea())
                        .departureDate(registerFlight.getFlightDetail().getDepartureDate())
                        .departureTime(registerFlight.getFlightDetail().getDepartureTime())
                        .build())
                .needPassport(registerFlight.getNeedPassport())
                .build();
    }
}
