package com.ljy.flightreservation.services.flightInfo.domain.exception;

public class InvalidFightInfoException extends IllegalArgumentException {
    public InvalidFightInfoException(String msg) {
        super(msg);
    }
}
