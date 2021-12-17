package com.ljy.flightreservation.services.reservation.application.external;

import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;

import java.util.Optional;

public interface FlightRepository {
    Optional<FlightInfo> getFlightInfo(long seq);
}
