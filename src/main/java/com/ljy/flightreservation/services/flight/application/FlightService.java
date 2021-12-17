package com.ljy.flightreservation.services.flight.application;

import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.flight.application.model.RegisterFlight;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.ljy.flightreservation.services.flight.domain.RegisterFlightValidator;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 운항 정보 서비스
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    // util
    private final RegisterFlightValidator registerFlightValidator;
    private final FlightMapper flightMapper;

    public FlightModel register(RegisterFlight registerFlight) {
        Flight flight = flightMapper.mapFrom(registerFlight);
        flight.register(registerFlightValidator);

        flightRepository.save(flight);
        FlightModel flightModel = flight.toModel();

        log.info("save flight into database : {}", flightModel);
        return flightModel;
    }
}
