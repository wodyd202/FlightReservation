package com.ljy.flightreservation.runner;

import com.ljy.flightreservation.services.airplane.application.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.services.airplane.domain.value.*;
import com.ljy.flightreservation.services.flightInfo.application.FlightInfoService;
import com.ljy.flightreservation.services.flightInfo.application.model.RegistrationFlightInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class AppRunner implements ApplicationRunner {
    @Autowired private FlightInfoService flightInfoService;
    @Autowired private AirplaneRepository airplaneRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        saveFlightInfo("airplaneCode_1", "제주", "대구", 6, 20, 07, 20, 56_500, false);
        saveFlightInfo("airplaneCode_2", "김포", "제주", 6, 30, 07, 50, 52_400, false);
        saveFlightInfo("airplaneCode_3", "김포", "제주", 7, 20, 8, 50, 56_400, false);
        saveFlightInfo("airplaneCode_4", "청주", "제주", 6, 20, 07, 20, 44_400, false);
        saveFlightInfo("airplaneCode_5", "김포", "부산", 6, 20, 07, 20, 44_400, false);
        saveFlightInfo("airplaneCode_6", "부산", "김포", 6, 20, 07, 20, 44_400, false);
        saveFlightInfo("airplaneCode_7", "부산", "김포", 6, 20, 07, 20, 44_400, false);
        saveFlightInfo("airplaneCode_8", "광주", "제주", 6, 20, 07, 20, 44_400, false);
        saveFlightInfo("airplaneCode_9", "광주", "부산", 6, 20, 07, 20, 44_400, false);
    }
    
    private void saveFlightInfo(String code,
                                String arrivalArea,
                                String departureArea,
                                int departureHour,
                                int departureMinute,
                                int arrivalHour,
                                int arrivalMinute,
                                int price,
                                boolean needPassport) {
        saveAirplane(code);
        RegistrationFlightInfo registerFlightInfo = RegistrationFlightInfo.builder()
                .airplaneCode(code)
                .arrivalArea(arrivalArea)
                .departureArea(departureArea)
                .departureHour(departureHour)
                .departureMinute(departureMinute)
                .arrivalHour(arrivalHour)
                .arrivalMinute(arrivalMinute)
                .price(price)
                .needPassport(needPassport)
                .build();
        flightInfoService.save(registerFlightInfo);
    }

    private void saveAirplane(String airplaneCode){
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
                .code(new AirplaneCode(airplaneCode))
                .corridorIndexes(new CorridorIndexes(Arrays.asList(1, 2)))
                .sits(new Sits(sits))
                .build();
        airplaneRepository.save(airplane);
    }
}
