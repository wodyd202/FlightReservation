package com.ljy.flightreservation.services.command.domain.exception;

public class BookerNotFoundException extends IllegalStateException {
    public BookerNotFoundException(String msg) {
        super(msg);
    }
}
