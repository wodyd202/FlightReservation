package com.ljy.flightreservation.airplane.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSearch {
    private String flightInfoCode;
    private LocalDate departureDate;
}
