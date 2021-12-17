package com.ljy.flightreservation.services.flight.application.model;

import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FlightModels {
    private List<FlightModel> flights;
    private long totalElement;

    @Builder
    public FlightModels(List<FlightModel> flights, long totalElement) {
        this.flights = flights;
        this.totalElement = totalElement;
    }
}
