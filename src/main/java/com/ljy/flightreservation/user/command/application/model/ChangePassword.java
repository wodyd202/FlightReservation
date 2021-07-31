package com.ljy.flightreservation.user.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    @NotBlank(message = "originPassword must not be empty")
    @Size(min = 5, max = 15, message = "originPassword size must be 5 or more and 15 or less")
    private String originPassword;

    @NotBlank(message = "changePassword must not be empty")
    @Size(min = 5, max = 15, message = "changePassword size must be 5 or more and 15 or less")
    private String changePassword;
}
