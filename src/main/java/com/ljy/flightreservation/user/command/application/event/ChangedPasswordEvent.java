package com.ljy.flightreservation.user.command.application.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangedPasswordEvent implements UserEvent{
    private final String id;
    private final String password;

    @Builder
    public ChangedPasswordEvent(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
