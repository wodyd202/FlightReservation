package com.ljy.flightreservation.airplane.domain.value;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AirplaneCode implements Serializable {
    private final String code;

    protected AirplaneCode(){code = null;};

    public AirplaneCode(String code){
        this.code = code;
    }

    public String get() {
        return code;
    }
}
