package com.ljy.flightreservation.flightInfo.application.model;

import com.ljy.flightreservation.flightInfo.domain.value.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlightInfoModel {
    private String code;

    private String arrivalArea;
    private String departureArea;

    private int departureHour;
    private int departureMinute;
    private int arrivalHour;
    private int arrivalMinute;

    private long price;

    private String airplaneCode;

    private String state;

    private String needPassport;

    @Builder
    public FlightInfoModel(FlightInfoCode code,
                           FlightDepartureArrivalArea area,
                           FlightTimes times,
                           BasePrice price,
                           FlightInfoState state,
                           AirplaneCode airplaneCode,
                           NeedPassport needPassport){
        this.code = code.get();
        this.arrivalArea = area.getArrivalArea();
        this.departureArea = area.getDepartureArea();
        this.departureHour = times.getDepartureHour();
        this.departureMinute = times.getDepartureMinute();
        this.arrivalHour = times.getArrivalHour();
        this.arrivalMinute = times.getArrivalMinute();
        this.price = price.get();
        this.airplaneCode = airplaneCode.get();
        this.state = state.toString();
        this.needPassport = needPassport.toString();
    }
}
