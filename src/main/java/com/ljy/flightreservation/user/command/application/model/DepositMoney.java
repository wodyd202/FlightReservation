package com.ljy.flightreservation.user.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class DepositMoney {

    @NotBlank(message = "money must not be empty")
    @Size(min = 1, message = "invalid money")
    private long money;
}
