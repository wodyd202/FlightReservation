package com.ljy.flightreservation.user.command.domain.exception;

public class InvalidUserIdException extends IllegalArgumentException {
    public InvalidUserIdException(String msg){
        super(msg);
    }
}
