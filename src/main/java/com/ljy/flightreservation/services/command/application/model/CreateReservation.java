package com.ljy.flightreservation.services.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservation {
    private String flightInfoCode;
    private List<String> reservationSits;
}
