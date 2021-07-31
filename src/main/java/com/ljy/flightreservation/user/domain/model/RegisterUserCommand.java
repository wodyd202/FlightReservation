package com.ljy.flightreservation.user.domain.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterUserCommand {
    private String id;
    private String password;
    private String passport;
}
