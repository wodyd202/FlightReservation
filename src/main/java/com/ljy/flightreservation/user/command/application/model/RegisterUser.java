package com.ljy.flightreservation.user.command.application.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterUser {
    private String id;
    private String password;
    private String email;
    private String passport;
}
