package com.ljy.flightreservation.services.airplane;

import com.ljy.flightreservation.services.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.services.airplane.domain.value.CorridorIndexes;
import com.ljy.flightreservation.services.airplane.domain.exception.InvalidSitException;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.services.airplane.domain.value.Sit;
import com.ljy.flightreservation.services.airplane.domain.value.SitType;
import com.ljy.flightreservation.services.airplane.domain.value.Sits;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirplaneTest {

    @Test
    @DisplayName("일반석이 아닌경우 추가요금이 반드시 붙음")
    void invalidExtraPrice(){
        assertThrows(InvalidSitException.class, ()->{
            new Sit("A0", SitType.BUSINESS,0);
        });
    }

    @Test
    @DisplayName("자리는 중복을 허용하지 않음")
    void dupSitCode(){
        List<Sit> sits = new ArrayList<>();
        sits.add(new Sit("A0", SitType.NOMAL,0));
        sits.add(new Sit("A0", SitType.NOMAL, 0));

        assertThrows(InvalidSitException.class,()->{
            new Sits(sits);
        });
    }

    @Test
    @DisplayName("항공기 자리는 적어도 1개 이상이여야함")
    void emptySits(){
        List<Sit> sits = new ArrayList<>();

        assertThrows(InvalidSitException.class,()->{
            new Sits(sits);
        });
    }

    @Test
    @DisplayName("항공기 등록시 자리 코드 뒷자리는 숫자만 허용함")
    void invalidBackSitCode(){
        List<Sit> sits = new ArrayList<>();
        sits.add(new Sit("AA", SitType.NOMAL,0));

        assertThrows(InvalidSitException.class,()->{
            new Sits(sits);
        });
    }

    @Test
    @DisplayName("항공기 등록시 자리 코드 앞자리는 A부터 Z까지만 허용")
    void invalidFrontSitCode(){
        List<Sit> sits = new ArrayList<>();
        sits.add(new Sit("a0", SitType.NOMAL,0));

        assertThrows(InvalidSitException.class,()->{
            new Sits(sits);
        });
    }

    @Test
    @DisplayName("항공기 등록")
    void registration(){
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
        Airplane airplane = Airplane.builder()
                .code(new AirplaneCode(UUID.randomUUID().toString()))
                .corridorIndexes(new CorridorIndexes(Arrays.asList(1,2)))
                .sits(new Sits(sits))
                .build();
        assertEquals(airplane.getTotalSitCount(), 18);
    }
}
