package com.ljy.flightreservation.flightInfo.domain.value;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FlightInfoCode implements Serializable {
    private final String code;

    protected FlightInfoCode(){code=null;}

    public FlightInfoCode(String code){
        this.code = code;
    }

    public String get() {
        return code;
    }
}
