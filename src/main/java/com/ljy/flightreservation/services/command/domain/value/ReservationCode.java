package com.ljy.flightreservation.services.command.domain.value;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(of = "code")
public class ReservationCode implements Serializable {
    private final String code;
    protected ReservationCode(){code=null;}

    public ReservationCode(String code) {
        this.code = code;
    }

    public String get() {
        return code;
    }
}
