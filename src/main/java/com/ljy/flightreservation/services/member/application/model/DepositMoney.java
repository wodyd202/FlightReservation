package com.ljy.flightreservation.services.member.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoney {

    @NotNull(message = "money must not be empty")
    @Min(value = 1, message = "invalid money")
    private Long money;
}
