package com.ljy.flightreservation.flightInfo.domain.exception;

public class InvalidFightInfoException extends IllegalArgumentException {
    public InvalidFightInfoException(String msg) {
        super(msg);
    }
}
