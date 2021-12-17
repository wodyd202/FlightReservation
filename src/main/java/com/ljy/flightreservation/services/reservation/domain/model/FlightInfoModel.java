package com.ljy.flightreservation.services.reservation.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightInfoModel {
    // 운항 고유 번호
    private long seq;

    private LocalDate departureDate;
    private int departureTime;

    private LocalDate arrivalDate;
    private int estimatedArrivalTime;

    private String arrivalArea;

    @Builder
    public FlightInfoModel(long seq,
                           LocalDate departureDate,
                           int departureTime,
                           LocalDate arrivalDate,
                           int estimatedArrivalTime,
                           String arrivalArea) {
        this.seq = seq;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.arrivalArea = arrivalArea;
    }
}
