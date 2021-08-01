package com.ljy.flightreservation.user.command.domain.exception;

public class AlreadyDeletedUserException extends IllegalStateException {
    public AlreadyDeletedUserException(String msg) {
        super(msg);
    }
}
