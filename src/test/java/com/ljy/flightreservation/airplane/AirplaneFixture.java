package com.ljy.flightreservation.airplane;

import com.ljy.flightreservation.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.domain.value.CorridorIndexes;
import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.airplane.domain.value.Sit;
import com.ljy.flightreservation.airplane.domain.value.SitType;
import com.ljy.flightreservation.airplane.domain.value.Sits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

abstract public class AirplaneFixture {
    public static Airplane.AirplaneBuilder aAirplane(){
        List<Sit> sits = new ArrayList<>();
        sits.add(new Sit("A0", SitType.NOMAL,0));
        sits.add(new Sit("A1", SitType.NOMAL,0));
        sits.add(new Sit("A2", SitType.NOMAL,0));
        sits.add(new Sit("A3", SitType.NOMAL,0));
        sits.add(new Sit("A4", SitType.NOMAL,0));
        sits.add(new Sit("A5", SitType.NOMAL,0));
        sits.add(new Sit("B0", SitType.NOMAL,0));
        sits.add(new Sit("B1", SitType.NOMAL,0));
        sits.add(new Sit("B2", SitType.NOMAL,0));
        sits.add(new Sit("B3", SitType.NOMAL,0));
        sits.add(new Sit("B4", SitType.NOMAL,0));
        sits.add(new Sit("B5", SitType.NOMAL,0));
        sits.add(new Sit("C0", SitType.NOMAL,0));
        sits.add(new Sit("C1", SitType.NOMAL,0));
        sits.add(new Sit("C2", SitType.NOMAL,0));
        sits.add(new Sit("C3", SitType.NOMAL,0));
        sits.add(new Sit("C4", SitType.NOMAL,0));
        sits.add(new Sit("C5", SitType.NOMAL,0));
        return Airplane.builder()
                        .code(new AirplaneCode(UUID.randomUUID().toString()))
                        .corridorIndexes(new CorridorIndexes(Arrays.asList(1,2)))
                        .sits(new Sits(sits));
    }
}
