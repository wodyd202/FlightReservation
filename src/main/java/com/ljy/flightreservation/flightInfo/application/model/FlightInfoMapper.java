package com.ljy.flightreservation.flightInfo.application.model;

import com.ljy.flightreservation.flightInfo.domain.agg.FlightInfo;
import com.ljy.flightreservation.flightInfo.domain.value.*;
import org.springframework.stereotype.Component;

@Component
public class FlightInfoMapper {
    final public FlightInfo mapFrom(RegistrationFlightInfo registrationFlightInfo){
        return FlightInfo.builder()
                .area(FlightDepartureArrivalArea.builder()
                        .departureArea(registrationFlightInfo.getDepartureArea())
                        .arrivalArea(registrationFlightInfo.getArrivalArea())
                        .build())
                .times(FlightTimes.builder()
                        .departureHour(registrationFlightInfo.getDepartureHour())
                        .departureMinute(registrationFlightInfo.getDepartureMinute())
                        .arrivalHour(registrationFlightInfo.getArrivalHour())
                        .arrivalMinute(registrationFlightInfo.getArrivalMinute())
                        .build())
                .price(BasePrice.won(registrationFlightInfo.getPrice()))
                .airplaneCode(new AirplaneCode(registrationFlightInfo.getAirplaneCode()))
                .needPassport(registrationFlightInfo.getNeedPassport() ? NeedPassport.YES : NeedPassport.NO)
                .build();
    }
}
