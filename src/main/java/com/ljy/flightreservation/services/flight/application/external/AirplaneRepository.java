package com.ljy.flightreservation.services.flight.application.external;

import com.ljy.flightreservation.services.flight.domain.AirplaneInfo;

import java.util.Optional;

public interface AirplaneRepository {
    Optional<AirplaneInfo> getAirplane(String airplaneCode);
}
