package com.ljy.flightreservation.airplane;

public class InvalidSitException extends IllegalArgumentException{
    public InvalidSitException(String msg){
        super(msg);
    }
}
