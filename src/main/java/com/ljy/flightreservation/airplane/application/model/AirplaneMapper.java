package com.ljy.flightreservation.airplane.application.model;

import com.ljy.flightreservation.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.airplane.domain.value.CorridorIndexes;
import com.ljy.flightreservation.airplane.domain.value.Sits;
import org.springframework.stereotype.Component;

@Component
final public class AirplaneMapper {
    public Airplane mapFrom(AirplaneCode code, RegistrationAirplane airplaneCommand){
        return Airplane.builder()
                .code(code)
                .corridorIndexes(new CorridorIndexes(airplaneCommand.getCorridorIndexes()))
                .sits(new Sits(airplaneCommand.getSits()))
                .build();
    }
}
