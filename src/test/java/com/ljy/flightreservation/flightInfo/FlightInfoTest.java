package com.ljy.flightreservation.flightInfo;

import com.ljy.flightreservation.flightInfo.domain.agg.FlightInfo;
import com.ljy.flightreservation.flightInfo.domain.exception.InvalidFightInfoException;
import com.ljy.flightreservation.flightInfo.domain.exception.InvalildFlightInfoException;
import com.ljy.flightreservation.flightInfo.domain.value.AirplaneCode;
import com.ljy.flightreservation.flightInfo.domain.value.BasePrice;
import com.ljy.flightreservation.flightInfo.domain.value.FlightDepartureArrivalArea;
import com.ljy.flightreservation.flightInfo.domain.value.FlightTimes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FlightInfoTest {
    @Autowired FlightInfoRepository repository;

    @Test
    @DisplayName("가격은 반드시 100원 이상이여야함")
    void invalidBasePrice(){
        assertThrows(InvalidFightInfoException.class, ()->{
            BasePrice.won(99);
        });
    }

    @Test
    @DisplayName("예약 정보 생성시 도착시각은 출발시각보다 커야함")
    void invalidTimes(){
        assertThrows(InvalidFightInfoException.class, ()->{
            FlightInfo.builder()
                    .area(FlightDepartureArrivalArea.builder()
                            .arrivalArea("제주도")
                            .departureArea("서울")
                            .build())
                    .times(FlightTimes.builder()
                            .departureHour(10)
                            .departureMinute(30)
                            .arrivalHour(9)
                            .departureMinute(10)
                            .build())
                    .price(BasePrice.won(30000))
                    .airplaneCode(new AirplaneCode("code"))
                    .build();
        });
    }

    @Test
    @DisplayName("예약 정보 생성시 출발지 및 도착지는 반드시 입력해야함")
    void invalidAreas() {
        assertThrows(InvalildFlightInfoException.class, ()->{
            FlightInfo.builder()
                    .area(FlightDepartureArrivalArea.builder()
                            .build())
                    .times(FlightTimes.builder()
                            .departureHour(10)
                            .departureMinute(30)
                            .arrivalHour(11)
                            .departureMinute(10)
                            .build())
                    .price(BasePrice.won(30000))
                    .airplaneCode(new AirplaneCode("code"))
                    .build();
        });
    }

    @Test
    @DisplayName("예약 정보 생성")
    void registration() {
        FlightInfo flightInfomation = FlightInfo.builder()
                .area(FlightDepartureArrivalArea.builder()
                        .arrivalArea("제주도")
                        .departureArea("서울")
                        .build())
                .times(FlightTimes.builder()
                        .departureHour(10)
                        .departureMinute(30)
                        .arrivalHour(11)
                        .departureMinute(10)
                        .build())
                .price(BasePrice.won(30000))
                .airplaneCode(new AirplaneCode("code"))
                .build();
        repository.save(flightInfomation);
    }
}
