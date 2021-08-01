package com.ljy.flightreservation.user.command.application.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DepositedMoneyEvent implements UserEvent{
    private final String id;
    private final long money;

    @Builder
    public DepositedMoneyEvent(String id, long money) {
        this.id = id;
        this.money = money;
    }
}
