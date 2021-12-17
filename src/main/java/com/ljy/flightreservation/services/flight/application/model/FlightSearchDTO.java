package com.ljy.flightreservation.services.flight.application.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class FlightSearchDTO {
    private int page;
    private Boolean past;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    private String arrivalArea;
}
