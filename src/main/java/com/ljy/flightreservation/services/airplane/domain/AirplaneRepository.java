package com.ljy.flightreservation.services.airplane.domain;

import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;

import java.util.Optional;

public interface AirplaneRepository {
    void save(Airplane airplane);
    Optional<Airplane> findById(AirplaneCode airplaneCode);
    Optional<AirplaneModel> findById(String airplaneCode);
}
