package com.ljy.flightreservation.services.flightInfo.application;

import com.ljy.flightreservation.services.flightInfo.application.model.Airplane;

import java.util.Optional;

public interface AirplaneRepository {
    Optional<Airplane> findByAirplaneCode(String airplaneCode);
}
