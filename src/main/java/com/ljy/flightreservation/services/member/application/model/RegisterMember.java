package com.ljy.flightreservation.services.member.application.model;

import com.ljy.flightreservation.services.member.application.model.ChangeMemberInfo;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterMember {
    private String id;
    private String password;
    private ChangeMemberInfo memberInfo;
}
