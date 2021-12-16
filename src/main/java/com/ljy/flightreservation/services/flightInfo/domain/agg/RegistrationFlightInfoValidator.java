package com.ljy.flightreservation.services.flightInfo.domain.agg;

import com.ljy.flightreservation.services.flightInfo.application.AirplaneRepository;
import com.ljy.flightreservation.services.flightInfo.domain.exception.AirplaneNotFoundException;
import com.ljy.flightreservation.services.flightInfo.domain.value.AirplaneCode;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFlightInfoValidator {
    private final AirplaneRepository airplaneRepository;

    public RegistrationFlightInfoValidator(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public void validation(AirplaneCode airplaneCode){
        airplaneRepository.findByAirplaneCode(airplaneCode.get())
                          .orElseThrow(()->new AirplaneNotFoundException("airplane not found"));
    }
}

