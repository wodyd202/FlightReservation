package com.ljy.flightreservation.services.flightInfo.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "code")
public class AirplaneCode {
    private final String code;

    protected AirplaneCode(){code=null;}
    public AirplaneCode(String code){
        this.code = code;
    }

    public String get() {
        return code;
    }
}
