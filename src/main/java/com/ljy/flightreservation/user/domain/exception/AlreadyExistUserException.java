package com.ljy.flightreservation.user.domain.exception;

public class AlreadyExistUserException extends IllegalArgumentException {
    public AlreadyExistUserException(String msg) {
        super(msg);
    }
}
