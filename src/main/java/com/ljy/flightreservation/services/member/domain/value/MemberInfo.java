package com.ljy.flightreservation.services.member.domain.value;

import com.ljy.flightreservation.services.member.domain.model.MemberInfoModel;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {
    private Email email;
    private Passport passport;

    public void changePassport(Passport passport) {
        this.passport = passport;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    @Builder
    public MemberInfo(Email email, Passport passport) {
        this.email = email;
        this.passport = passport;
    }

    public MemberInfoModel toModel() {
        return MemberInfoModel.builder()
                .email(email)
                .passport(passport)
                .build();
    }
}
