package com.ljy.flightreservation.user.query.domain.exception;

public class UserNotFoundException extends IllegalStateException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
