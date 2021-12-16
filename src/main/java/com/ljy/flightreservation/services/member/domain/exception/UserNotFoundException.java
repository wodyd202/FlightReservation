package com.ljy.flightreservation.services.member.domain.exception;

public class UserNotFoundException extends IllegalStateException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
