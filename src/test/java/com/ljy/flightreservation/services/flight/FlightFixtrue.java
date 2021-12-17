package com.ljy.flightreservation.services.flight;

import com.ljy.flightreservation.services.flight.domain.AirplaneInfo;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightDetail;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;

import java.time.LocalDate;

public class FlightFixtrue {
    public static Flight.FlightBuilder aFlight(){
        return Flight.builder()
                .airplaneInfo(AirplaneInfo.builder()
                        .code("airplaneCode")
                        .build())
                .flightDetail(FlightDetail.builder()
                        .arrivalDate(LocalDate.now())
                        .estimatedArrivalTime(23)
                        .arrivalArea("제주 공항")
                        .departureDate(LocalDate.now())
                        .departureTime(21)
                        .build())
                .needPassport(NeedPassport.YES);
    }
}
