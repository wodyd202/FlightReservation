package com.ljy.flightreservation.reservation.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
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

    private String needPassport;
}
