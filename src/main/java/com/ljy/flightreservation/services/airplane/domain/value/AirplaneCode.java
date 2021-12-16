package com.ljy.flightreservation.services.airplane.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AirplaneCode implements Serializable {
    private String code;

    public static AirplaneCode of(String code){return new AirplaneCode(code);}

    private AirplaneCode(String code){
        this.code = code;
    }

    public String get() {
        return code;
    }
}
