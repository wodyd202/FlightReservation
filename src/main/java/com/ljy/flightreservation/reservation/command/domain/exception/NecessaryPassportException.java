package com.ljy.flightreservation.reservation.command.domain.exception;

public class NecessaryPassportException extends IllegalStateException {
    public NecessaryPassportException(String msg) {
        super(msg);
    }
}
