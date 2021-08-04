package com.ljy.flightreservation.reservation.command.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "price")
public class Price {
    private final long price;

    protected Price() {
        price = 0;
    }

    private Price(long won) {
        this.price = won;
    }

    public static Price won(long won) {
        return new Price(won);
    }
}
