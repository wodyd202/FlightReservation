package com.ljy.flightreservation.services.command.application;

import com.ljy.flightreservation.services.command.application.model.AirplaneModel;

import java.util.Optional;

public interface AirplaneRepository {
    Optional<AirplaneModel> findByAirplaneCode(String airplaneCode);
}
