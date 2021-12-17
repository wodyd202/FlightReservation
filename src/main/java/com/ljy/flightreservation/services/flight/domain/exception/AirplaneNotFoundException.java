package com.ljy.flightreservation.services.flight.domain.exception;

public class AirplaneNotFoundException extends IllegalStateException {
    public AirplaneNotFoundException(String msg) {
        super(msg);
    }
}
