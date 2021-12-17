package com.ljy.flightreservation.services.flight.domain;

import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * 운행 도메인 테스트
 */
public class Flight_Test {

    @Test
    @DisplayName("도착 지역은 빈 값을 허용하지 않음")
    void emptyArrivalArea(){
        assertThrows(IllegalArgumentException.class, ()->{
            FlightDetail.builder()
                    .arrivalDate(LocalDate.now())
                    .estimatedArrivalTime(23)
                    .arrivalArea("")
                    .departureDate(LocalDate.now())
                    .departureTime(21)
                    .build();
        });
    }

    @Test
    @DisplayName("출발 일자와 도착일자가 같다면 출발 시간이 도착 예정 시각보다 작아야함")
    void invalidDepartureTime(){
        assertThrows(IllegalArgumentException.class, ()->{
            FlightDetail.builder()
                    .arrivalDate(LocalDate.now())
                    .estimatedArrivalTime(22)
                    .arrivalArea("제주 공항")
                    .departureDate(LocalDate.now())
                    .departureTime(23)
                    .build();
        });
    }

    @Test
    @DisplayName("출발 일자는 도착일자 보다 같거나 작아야함")
    void invalidDepartureDate(){
        assertThrows(IllegalArgumentException.class, ()->{
            FlightDetail.builder()
                    .arrivalDate(LocalDate.now())
                    .estimatedArrivalTime(23)
                    .arrivalArea("제주 공항")
                    .departureDate(LocalDate.now().plusDays(1))
                    .departureTime(21)
                    .build();
        });
    }

    @Test
    @DisplayName("운행 도메인 생성")
    void newFlight(){
        // when
        Flight flight = Flight.builder()
                .basePrice(BasePrice.won(500000))
                .airplaneInfo(AirplaneInfo.builder()
                        .code("airplaneCode")
                        .build())
                .flightDetail(FlightDetail.builder()
                        .arrivalDate(LocalDate.now())
                        .estimatedArrivalTime(23)
                        .arrivalArea("제주 공항")
                        .departureDate(LocalDate.now())
                        .departureTime(21)
                        .build())
                .needPassport(NeedPassport.YES)
                .build();
        flight.register(mock(RegisterFlightValidator.class));
        FlightModel flightModel = flight.toModel();

        // then
        assertEquals(flightModel.getAirplaneInfo().getCode(), "airplaneCode");
        assertEquals(flightModel.getFlightDetail().getArrivalDate(), LocalDate.now());
        assertEquals(flightModel.getFlightDetail().getEstimatedArrivalTime(), 23);
        assertEquals(flightModel.getFlightDetail().getArrivalArea(), "제주 공항");
        assertEquals(flightModel.getFlightDetail().getDepartureDate(), LocalDate.now());
        assertEquals(flightModel.getFlightDetail().getDepartureTime(), 21);
        assertEquals(flightModel.getNeedPassport(), NeedPassport.YES);
        assertEquals(flightModel.getState(), FlightState.IN_OPER);
    }
}
