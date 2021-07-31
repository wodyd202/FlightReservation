package com.ljy.flightreservation.user.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class Withdrawal {

    @NotBlank(message = "originPassword must not be empty")
    @Size(min = 5, max = 15, message = "originPassword size must be 5 or more and 15 or less")
    private String originPassword;
}
