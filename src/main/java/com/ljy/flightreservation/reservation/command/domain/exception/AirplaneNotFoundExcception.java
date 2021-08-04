package com.ljy.flightreservation.reservation.command.domain.exception;

public class AirplaneNotFoundExcception extends IllegalStateException {
    public AirplaneNotFoundExcception(String msg) {
        super(msg);
    }
}
