package com.ljy.flightreservation.services.command.domain.value;

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
