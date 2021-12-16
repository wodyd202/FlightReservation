package com.ljy.flightreservation.services.command.domain.value;

public class FlightInfoCode {
    private final String code;

    protected FlightInfoCode(){
        code = null;
    }

    public FlightInfoCode(String code) {
        this.code = code;
    }

    public String get() {
        return code;
    }
}
