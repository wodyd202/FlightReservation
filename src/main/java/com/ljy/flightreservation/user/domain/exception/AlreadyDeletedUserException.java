package com.ljy.flightreservation.user.domain.exception;

public class AlreadyDeletedUserException extends IllegalArgumentException {
    public AlreadyDeletedUserException(String msg) {
        super(msg);
    }
}
