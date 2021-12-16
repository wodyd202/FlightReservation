package com.ljy.flightreservation.services.member.application.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangedPassportEvent implements UserEvent{
    private final String id;
    private final String passport;

    @Builder
    public ChangedPassportEvent(String id, String passport) {
        this.id = id;
        this.passport = passport;
    }
}
