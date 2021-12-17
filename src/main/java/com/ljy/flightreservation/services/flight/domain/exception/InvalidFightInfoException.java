package com.ljy.flightreservation.services.flight.domain.exception;

public class InvalidFightInfoException extends IllegalArgumentException {
    public InvalidFightInfoException(String msg) {
        super(msg);
    }
}
