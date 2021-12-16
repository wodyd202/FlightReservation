package com.ljy.flightreservation.services.airplane.domain.exception;

public class InvalidSitException extends IllegalArgumentException{
    public InvalidSitException(String msg){
        super(msg);
    }
}
