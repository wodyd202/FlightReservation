package com.ljy.flightreservation.services.reservation.application.model;

import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationModels {
    private List<ReservationModel> reservations;
    private long totalElement;

    @Builder
    public ReservationModels(List<ReservationModel> reservations, long totalElement) {
        this.reservations = reservations;
        this.totalElement = totalElement;
    }
}
