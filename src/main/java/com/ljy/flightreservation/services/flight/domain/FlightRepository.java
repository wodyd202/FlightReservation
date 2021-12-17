package com.ljy.flightreservation.services.flight.domain;

import com.ljy.flightreservation.services.flight.application.model.FlightSearchDTO;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {
    void save(Flight flight);

    List<FlightModel> findAll(FlightSearchDTO flightSearchDTO);
    long countAll(FlightSearchDTO flightSearchDTO);
    Optional<FlightModel> findById(Long flightSeq);
}
