package com.ljy.flightreservation.services.flight.application;

import com.ljy.flightreservation.services.airplane.AirplaneFixture;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.RegisterAirplaneValidator;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.services.flight.FlightIntegrationTest;
import com.ljy.flightreservation.services.flight.application.model.ChangeFlightDetail;
import com.ljy.flightreservation.services.flight.application.model.RegisterFlight;
import com.ljy.flightreservation.services.flight.domain.FlightState;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.ljy.flightreservation.services.airplane.AirplaneFixture.aAirplane;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class FlightService_Test extends FlightIntegrationTest {
    @Autowired FlightService flightService;

    // external
    @Autowired AirplaneRepository airplaneRepository;

    @Test
    @DisplayName("운항 정보 등록")
    void register(){
        // given
        saveAirplane("airplaneCode");
        RegisterFlight registerFlight = RegisterFlight.builder()
                .flightDetail(ChangeFlightDetail.builder()
                        .arrivalDate(LocalDate.now())
                        .estimatedArrivalTime(23)
                        .arrivalArea("제주 공항")
                        .departureDate(LocalDate.now())
                        .departureTime(21)
                        .build())
                .needPassport(NeedPassport.YES)
                .airplaneCode("airplaneCode")
                .build();

        // when
        FlightModel flightModel = flightService.register(registerFlight);

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

    private void saveAirplane(String airplaneCode) {
        Airplane airplane = aAirplane().code(AirplaneCode.of(airplaneCode)).build();
        airplane.register(mock(RegisterAirplaneValidator.class));
        airplaneRepository.save(airplane);
    }
}
