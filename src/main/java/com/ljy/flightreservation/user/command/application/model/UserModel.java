package com.ljy.flightreservation.user.command.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.agg.UserRole;
import com.ljy.flightreservation.user.command.domain.value.UserState;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserModel {
    private String id;
    @JsonIgnore
    private String password;
    private UserState userState;
    private String passport;
    private long money;
    private LocalDateTime createDateTime;
    private UserRole role;

    public UserModel(User user) {
        this.id = user.getId().get();
        this.password = user.getPassword().get();
        this.userState = user.getState();
        if (!Objects.isNull(user.getPassport())) {
            this.passport = user.getPassport().get();
        }
        this.money = user.getMoney().get();
        this.createDateTime = user.getCreateDateTime();
        this.role = user.getRole();
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public UserState getUserState() {
        return userState;
    }

    public String getPassport() {
        return passport;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public long getMoney() { return money; }

    public UserRole getRole() { return role; }
}
