package com.ljy.flightreservation.services.flightInfo.domain.exception;

public class AirplaneNotFoundException extends IllegalStateException {
    public AirplaneNotFoundException(String msg) {
        super(msg);
    }
}
