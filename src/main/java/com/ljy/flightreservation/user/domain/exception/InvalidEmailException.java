package com.ljy.flightreservation.user.domain.exception;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException(String msg) {
        super(msg);
    }
}
