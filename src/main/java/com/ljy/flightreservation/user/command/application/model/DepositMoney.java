package com.ljy.flightreservation.user.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoney {

    @NotNull(message = "money must not be empty")
    @Min(value = 1, message = "invalid money")
    private Long money;
}
