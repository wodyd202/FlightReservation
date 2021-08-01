package com.ljy.flightreservation.airplane;

import com.ljy.flightreservation.airplane.command.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.command.domain.value.AirplaneCode;

import java.util.Optional;

public interface AirplaneRepository {
    Optional<Airplane> findByAirplaneCode(AirplaneCode code);
    void save(Airplane airplane);
}