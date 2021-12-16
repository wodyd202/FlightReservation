package com.ljy.flightreservation.services.airplane.application.model;

import com.ljy.flightreservation.services.airplane.domain.value.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AirplaneModel {
    private String airplaneCode;
    private List<Integer> corridorIndexes;
    private List<Sit> sits;
    private String state;

    @Builder
    public AirplaneModel(AirplaneCode airplaneCode,
                         CorridorIndexes corridorIndexes,
                         Sits sits,
                         AirplaneState state){
        this.airplaneCode = airplaneCode.get();
        this.corridorIndexes = corridorIndexes.get();
        this.sits = sits.get();
        this.state = state.toString();
   }
}
