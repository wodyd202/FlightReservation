package com.ljy.flightreservation.user.command.application.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.flightreservation.user.command.application.model.UserModel;
import com.ljy.flightreservation.user.command.domain.agg.UserRole;
import com.ljy.flightreservation.user.command.domain.value.UserState;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class RegisteredUserEvent implements UserEvent{
    private final String id;
    private final String password;
    private final String userState;
    private final String passport;
    private final long money;
    private final LocalDateTime createDateTime;
    private final String role;

    @Builder
    public RegisteredUserEvent(String id, String password, UserState userState, String passport, long money, LocalDateTime createDateTime, UserRole role) {
        this.id = id;
        this.password = password;
        this.userState = userState.toString();
        this.passport = passport;
        this.money = money;
        this.createDateTime = createDateTime;
        this.role = role.toString();
    }
}
