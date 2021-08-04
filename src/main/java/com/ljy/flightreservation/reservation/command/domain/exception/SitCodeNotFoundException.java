package com.ljy.flightreservation.reservation.command.domain.exception;

public class SitCodeNotFoundException extends IllegalStateException{
    public SitCodeNotFoundException(String msg){
        super(msg);
    }
}
