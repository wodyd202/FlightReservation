package com.ljy.flightreservation.user.command.application.event;

import lombok.Getter;

@Getter
public class WithdrawaledEvent implements UserEvent{
    private final String id;

    public WithdrawaledEvent(String id) {
        this.id = id;
    }
}
