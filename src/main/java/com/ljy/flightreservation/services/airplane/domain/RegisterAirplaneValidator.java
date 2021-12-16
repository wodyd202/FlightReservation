package com.ljy.flightreservation.services.airplane.domain;

import com.ljy.flightreservation.services.airplane.domain.exception.AlreadyExistAirplaneException;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterAirplaneValidator {
    private final AirplaneRepository airplaneRepository;

    public void validation(AirplaneCode code) {
        verifyNotExistAirplane(code);
    }

    private void verifyNotExistAirplane(AirplaneCode code) {
        if(airplaneRepository.findById(code).isPresent()){
            throw new AlreadyExistAirplaneException();
        }
    }
}
