package com.ljy.flightreservation.reservation.command.domain.value;

public class Booker {
    private final String id;

    protected Booker(){
        id = null;
    }

    public Booker(String id) {
        this.id = id;
    }

    public String get() {
        return id;
    }
}
