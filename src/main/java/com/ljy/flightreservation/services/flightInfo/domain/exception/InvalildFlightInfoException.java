package com.ljy.flightreservation.services.flightInfo.domain.exception;

public class InvalildFlightInfoException extends IllegalArgumentException {
    public InvalildFlightInfoException(String msg) {
        super(msg);
    }
}
