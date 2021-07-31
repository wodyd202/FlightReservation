package com.ljy.flightreservation.user.command.domain.exception;

public class AlreadyDeletedUserException extends IllegalArgumentException {
    public AlreadyDeletedUserException(String msg) {
        super(msg);
    }
}
