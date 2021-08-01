package com.ljy.flightreservation.airplane;

import com.ljy.flightreservation.airplane.command.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.command.domain.value.AirplaneCode;
import com.ljy.flightreservation.airplane.command.domain.value.Sit;
import com.ljy.flightreservation.airplane.command.domain.value.SitType;
import com.ljy.flightreservation.airplane.command.domain.value.Sits;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirplaneTest {

    @Test
    @DisplayName("항공기 등록")
    void registration(){
        List<Sit> sits = new ArrayList<>();
        sits.add(new Sit("A0", SitType.NOMAL));
        sits.add(new Sit("A1", SitType.NOMAL));
        sits.add(new Sit("A2", SitType.NOMAL));
        sits.add(new Sit("A3", SitType.NOMAL));
        sits.add(new Sit("A4", SitType.NOMAL));
        sits.add(new Sit("A5", SitType.NOMAL));
        sits.add(new Sit("B0", SitType.NOMAL));
        sits.add(new Sit("B1", SitType.NOMAL));
        sits.add(new Sit("B2", SitType.NOMAL));
        sits.add(new Sit("B3", SitType.NOMAL));
        sits.add(new Sit("B4", SitType.NOMAL));
        sits.add(new Sit("B5", SitType.NOMAL));
        sits.add(new Sit("C0", SitType.NOMAL));
        sits.add(new Sit("C1", SitType.NOMAL));
        sits.add(new Sit("C2", SitType.NOMAL));
        sits.add(new Sit("C3", SitType.NOMAL));
        sits.add(new Sit("C4", SitType.NOMAL));
        sits.add(new Sit("C5", SitType.NOMAL));
        Airplane airplane = Airplane.builder()
                .code(new AirplaneCode(UUID.randomUUID().toString()))
                .corridorIndexes(Arrays.asList(1,2))
                .sits(new Sits(sits))
                .build();
        assertEquals(airplane.getTotalSitCount(), 18);
    }
}
