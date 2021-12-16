package com.ljy.flightreservation.services.command.application;

import com.ljy.flightreservation.services.command.application.model.FlightInfoModel;

import java.util.Optional;

public interface FlightInfoRepository {
    Optional<FlightInfoModel> findByFlightInfoCode(String flightInfoCode);
}
