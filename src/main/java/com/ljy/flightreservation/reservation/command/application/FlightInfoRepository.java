package com.ljy.flightreservation.reservation.command.application;

import com.ljy.flightreservation.reservation.command.application.model.FlightInfoModel;

import java.util.Optional;

public interface FlightInfoRepository {
    Optional<FlightInfoModel> findByFlightInfoCode(String flightInfoCode);
}
