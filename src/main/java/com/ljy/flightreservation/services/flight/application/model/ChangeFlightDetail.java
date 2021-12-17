package com.ljy.flightreservation.services.flight.application.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChangeFlightDetail {
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    private int departureTime;
    private int estimatedArrivalTime;

    private String arrivalArea;
}
