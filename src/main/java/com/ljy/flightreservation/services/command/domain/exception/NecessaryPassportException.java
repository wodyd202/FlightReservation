package com.ljy.flightreservation.services.command.domain.exception;

public class NecessaryPassportException extends IllegalStateException {
    public NecessaryPassportException(String msg) {
        super(msg);
    }
}
