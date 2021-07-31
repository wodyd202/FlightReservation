package com.ljy.flightreservation.user.command.domain.exception;

public class InvalidPassportException extends IllegalArgumentException {
    public InvalidPassportException(String msg){
        super(msg);
    }
}
