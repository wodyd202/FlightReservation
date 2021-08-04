package com.ljy.flightreservation.reservation.command.domain.exception;

public class FlightInfoNotFoundException extends IllegalStateException {
    public FlightInfoNotFoundException(String msg) {
        super(msg);
    }
}
