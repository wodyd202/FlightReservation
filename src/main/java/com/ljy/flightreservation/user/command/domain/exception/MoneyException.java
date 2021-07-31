package com.ljy.flightreservation.user.command.domain.exception;

public class MoneyException extends IllegalStateException {
    public MoneyException(String msg) {
        super(msg);
    }
}
