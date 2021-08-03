package com.ljy.flightreservation.flightInfo.application;

import com.ljy.flightreservation.flightInfo.application.model.Airplane;

import java.util.Optional;

public interface AirplaneRepository {
    Optional<Airplane> findByAirplaneCode(String airplaneCode);
}
