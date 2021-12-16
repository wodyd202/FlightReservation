package com.ljy.flightreservation.services.member.application.model;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMember {
    private ChangePassword changePassword;
}
