package com.ljy.flightreservation.user.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassport {
    @NotBlank(message = "changePassport must not be empty")
    @Pattern(regexp = "([a-zA-Z]{1}|[a-zA-Z]{2})\\d{8}", message = "invalid passport")
    private String changePassport;
}
