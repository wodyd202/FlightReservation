package com.ljy.flightreservation.user.command.domain.exception;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException(String msg) {
        super(msg);
    }
}
