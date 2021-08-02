package com.ljy.flightreservation.airplane.application;

import com.ljy.flightreservation.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;

import java.util.Optional;

public interface AirplaneRepository {
    void save(Airplane airplane);
    Optional<Airplane> findByAirplaneCode(AirplaneCode code);
}
