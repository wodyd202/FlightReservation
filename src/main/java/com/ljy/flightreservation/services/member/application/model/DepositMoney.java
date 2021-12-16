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
    @Min(value = 1000, message = "입금은 1000이상 부터 가능합니다.")
    private long money;
}
