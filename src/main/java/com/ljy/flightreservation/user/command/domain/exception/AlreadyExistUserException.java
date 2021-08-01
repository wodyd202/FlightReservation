package com.ljy.flightreservation.user.command.domain.exception;

public class AlreadyExistUserException extends IllegalStateException {
    public AlreadyExistUserException(String msg) {
        super(msg);
    }
}
