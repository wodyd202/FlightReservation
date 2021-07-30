package com.ljy.flightreservation.user.domain.exception;

public class InvalidPassportException extends IllegalArgumentException {
    public InvalidPassportException(String msg){
        super(msg);
    }
}
