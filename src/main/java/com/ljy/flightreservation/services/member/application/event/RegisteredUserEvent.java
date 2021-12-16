package com.ljy.flightreservation.services.member.application.event;

import com.ljy.flightreservation.services.member.domain.value.UserRole;
import com.ljy.flightreservation.services.member.domain.value.MemberState;
import lombok.Builder;
import lombok.Getter;

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
    public RegisteredUserEvent(String id, String password, MemberState userState, String passport, long money, LocalDateTime createDateTime, UserRole role) {
        this.id = id;
        this.password = password;
        this.userState = userState.toString();
        this.passport = passport;
        this.money = money;
        this.createDateTime = createDateTime;
        this.role = role.toString();
    }
}
