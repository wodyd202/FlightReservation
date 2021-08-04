package com.ljy.flightreservation.reservation.command.domain.exception;

public class UnBalanceSufficientException extends IllegalStateException {
    public UnBalanceSufficientException(String msg) {
        super(msg);
    }
}
