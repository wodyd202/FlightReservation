package com.ljy.flightreservation.services.airplane.application;

import com.ljy.flightreservation.services.airplane.application.model.RegisterAirplane;
import com.ljy.flightreservation.services.airplane.domain.exception.AlreadyExistAirplaneException;
import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ljy.flightreservation.services.airplane.AirplaneFixture.aAirplane;
import static com.ljy.flightreservation.services.airplane.AirplaneFixture.aRegisterAirplane;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 항공기 서비스 테스트
 */
@SpringBootTest
public class AirplaneService_Test extends AirplaneIntegrationTest {
    @Autowired
    AirplaneService airplaneService;

    @Test
    @DisplayName("항공기 등록")
    void register(){
        // given
        RegisterAirplane registerAirplane = aRegisterAirplane().code("noExistCode").build();

        // when
        AirplaneModel airplaneModel = airplaneService.register(registerAirplane);

        // then
        assertNotNull(airplaneModel);
    }

    @Test
    @DisplayName("항공기 등록시 중복된 코드가 존재하면 에러")
    void alreadyExistCode(){
        // given
        newAirplane(aAirplane().code(AirplaneCode.of("existCode")));
        RegisterAirplane registerAirplane = aRegisterAirplane().code("existCode").build();

        assertThrows(AlreadyExistAirplaneException.class, ()->{
            airplaneService.register(registerAirplane);
        });
    }
}
