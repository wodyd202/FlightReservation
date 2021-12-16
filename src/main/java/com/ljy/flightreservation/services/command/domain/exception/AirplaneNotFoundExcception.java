package com.ljy.flightreservation.services.command.domain.exception;

public class AirplaneNotFoundExcception extends IllegalStateException {
    public AirplaneNotFoundExcception(String msg) {
        super(msg);
    }
}
