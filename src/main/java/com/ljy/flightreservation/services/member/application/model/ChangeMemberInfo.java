package com.ljy.flightreservation.services.member.application.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMemberInfo {
    private String email;
    private String passport;
}
