package com.ljy.flightreservation.services.airplane.application;

import com.ljy.flightreservation.services.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;

import java.util.Optional;

public interface AirplaneRepository {
    void save(Airplane airplane);
    Optional<Airplane> findByAirplaneCode(AirplaneCode code);
}
