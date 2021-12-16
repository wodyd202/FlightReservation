package com.ljy.flightreservation.services.command.domain.exception;

public class SitCodeNotFoundException extends IllegalStateException{
    public SitCodeNotFoundException(String msg){
        super(msg);
    }
}
