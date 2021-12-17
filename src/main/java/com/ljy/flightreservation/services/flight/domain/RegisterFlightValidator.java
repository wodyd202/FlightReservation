package com.ljy.flightreservation.services.flight.domain;

import com.ljy.flightreservation.services.airplane.domain.exception.AirplaneNotFoundException;
import com.ljy.flightreservation.services.flight.application.external.AirplaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterFlightValidator {
    private final AirplaneRepository airplaneRepository;

    public void validation(AirplaneInfo airplaneInfo) {
        airplaneRepository.getAirplane(airplaneInfo.getCode()).orElseThrow(AirplaneNotFoundException::new);
    }
}
