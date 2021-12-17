package com.ljy.flightreservation.services.flight.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightDetailModel {
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    private int departureTime;
    private int estimatedArrivalTime;

    private String arrivalArea;

    @Builder
    public FlightDetailModel(LocalDate departureDate, LocalDate arrivalDate, int departureTime, int estimatedArrivalTime, String arrivalArea) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.arrivalArea = arrivalArea;
    }
}
