package com.ljy.flightreservation.user.domain.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
