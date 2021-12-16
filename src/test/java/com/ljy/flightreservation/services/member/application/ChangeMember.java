package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.application.model.ChangePassword;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMember {
    private ChangePassword changePassword;
}
