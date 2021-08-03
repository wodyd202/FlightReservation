package com.ljy.flightreservation.airplane.domain.exception;

public class InvalidSitException extends IllegalArgumentException{
    public InvalidSitException(String msg){
        super(msg);
    }
}
