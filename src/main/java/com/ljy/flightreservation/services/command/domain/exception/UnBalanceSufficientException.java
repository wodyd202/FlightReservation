package com.ljy.flightreservation.services.command.domain.exception;

public class UnBalanceSufficientException extends IllegalStateException {
    public UnBalanceSufficientException(String msg) {
        super(msg);
    }
}
