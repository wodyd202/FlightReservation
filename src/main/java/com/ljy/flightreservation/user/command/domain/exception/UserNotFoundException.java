package com.ljy.flightreservation.user.command.domain.exception;

public class UserNotFoundException extends IllegalStateException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
