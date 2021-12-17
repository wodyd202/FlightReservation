package com.ljy.flightreservation.services.flight.domain.exception;

public class InvalildFlightInfoException extends IllegalArgumentException {
    public InvalildFlightInfoException(String msg) {
        super(msg);
    }
}
