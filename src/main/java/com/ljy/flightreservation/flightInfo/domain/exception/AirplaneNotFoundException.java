package com.ljy.flightreservation.flightInfo.domain.exception;

public class AirplaneNotFoundException extends IllegalStateException {
    public AirplaneNotFoundException(String msg) {
        super(msg);
    }
}
