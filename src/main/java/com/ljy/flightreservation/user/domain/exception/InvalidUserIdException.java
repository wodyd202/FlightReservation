package com.ljy.flightreservation.user.domain.exception;

public class InvalidUserIdException extends IllegalArgumentException {
    public InvalidUserIdException(String msg){
        super(msg);
    }
}
