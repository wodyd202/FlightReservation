package com.ljy.flightreservation.reservation.command.domain.value;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class SitCodes {
    private List<String> sits;

    protected SitCodes() {
        sits = new ArrayList<>();
    }

    public SitCodes(List<String> sits) {
        this.sits = sits;
    }

    public List<String> get() {
        return sits;
    }
}
