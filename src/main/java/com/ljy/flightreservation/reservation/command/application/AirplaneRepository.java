package com.ljy.flightreservation.reservation.command.application;

import com.ljy.flightreservation.reservation.command.application.model.AirplaneModel;

import java.util.Optional;

public interface AirplaneRepository {
    Optional<AirplaneModel> findByAirplaneCode(String airplaneCode);
}
