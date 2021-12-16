package com.ljy.flightreservation.services.member.domain.model;

import com.ljy.flightreservation.services.member.domain.value.Email;
import com.ljy.flightreservation.services.member.domain.value.Passport;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoModel {
    private String email;
    private String passport;

    @Builder
    public MemberInfoModel(Email email, Passport passport) {
        this.email = email.get();
        this.passport = passport.get();
    }
}
