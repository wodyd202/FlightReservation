package com.ljy.flightreservation.user.query.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.flightreservation.user.command.application.event.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "QueryUsers")
@DynamicUpdate
public class QueryUser {
    @Id
    private String id;

    @JsonIgnore
    private String password;
    private String userState;
    private String passport;
    private long money;
    private LocalDateTime createDateTime;
    private String role;

    protected void on(RegisteredUserEvent event){
        id = event.getId();
        password = event.getPassword();
        passport = event.getPassport();
        userState = event.getUserState();
        money = event.getMoney();
        createDateTime = event.getCreateDateTime();
        role = event.getRole().toString();
    }

    protected void on(ChangedPasswordEvent event){
        password = event.getPassword();
    }

    protected void on(ChangedPassportEvent event){
        passport = event.getPassport();
    }

    protected void on(DepositedMoneyEvent event){
        money = event.getMoney();
    }

    protected void on(TemporaryedPasswordEvent event){
        password = event.getPassword();
    }

    protected void on(WithdrawaledEvent event){
        userState = "DELETED";
    }
}
