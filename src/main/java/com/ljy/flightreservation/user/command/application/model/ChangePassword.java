package com.ljy.flightreservation.user.command.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private String originPassword;
    private String changePassword;
}
