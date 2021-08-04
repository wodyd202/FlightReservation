package com.ljy.flightreservation.reservation.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirplaneModel {
    private String airplaneCode;
    private List<SitModel> sits;
}
