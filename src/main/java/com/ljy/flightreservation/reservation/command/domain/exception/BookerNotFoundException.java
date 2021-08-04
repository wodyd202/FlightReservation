package com.ljy.flightreservation.reservation.command.domain.exception;

public class BookerNotFoundException extends IllegalStateException {
    public BookerNotFoundException(String msg) {
        super(msg);
    }
}
