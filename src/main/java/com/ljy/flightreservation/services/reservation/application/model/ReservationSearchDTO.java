package com.ljy.flightreservation.services.reservation.application.model;

import com.ljy.flightreservation.services.reservation.domain.value.ReservationState;
import lombok.Data;

@Data
public class ReservationSearchDTO {
    private int page;
    private String arrivalArea;
    private Boolean past;
    private ReservationState state;
}
