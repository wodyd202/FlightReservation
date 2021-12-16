package com.ljy.flightreservation.services.airplane.application;

import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.RegisterAirplaneValidator;
import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class AirplaneIntegrationTest {
    @Autowired
    AirplaneRepository airplaneRepository;

    protected void newAirplane(Airplane.AirplaneBuilder airplaneBuilder){
        Airplane airplane = airplaneBuilder.build();
        airplane.register(mock(RegisterAirplaneValidator.class));
        AirplaneModel airplaneModel = airplane.toModel();
        if(!airplaneRepository.findById(AirplaneCode.of(airplaneModel.getCode())).isPresent()) {
            airplaneRepository.save(airplane);
        }
    }
}
