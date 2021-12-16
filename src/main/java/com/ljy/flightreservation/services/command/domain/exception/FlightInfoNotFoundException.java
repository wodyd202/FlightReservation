package com.ljy.flightreservation.services.command.domain.exception;

public class FlightInfoNotFoundException extends IllegalStateException {
    public FlightInfoNotFoundException(String msg) {
        super(msg);
    }
}
