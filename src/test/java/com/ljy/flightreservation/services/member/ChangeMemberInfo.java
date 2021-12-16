package com.ljy.flightreservation.services.member;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMemberInfo {
    private String email;
    private String passport;
}
