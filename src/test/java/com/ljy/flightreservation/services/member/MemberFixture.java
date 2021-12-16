package com.ljy.flightreservation.services.member;

import com.ljy.flightreservation.services.member.application.model.ChangeMemberInfo;
import com.ljy.flightreservation.services.member.application.model.RegisterMember;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.value.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberFixture {
    public static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static RegisterMember.RegisterMemberBuilder aRegisterMember(){
        return RegisterMember.builder()
                .id("registermember")
                .password("password")
                .memberInfo(ChangeMemberInfo.builder()
                        .email("registermember@google.com")
                        .passport("")
                        .build());
    }

    public static Member.MemberBuilder aMember() {
        return Member.builder()
                .id(MemberId.of("memberid"))
                .password(Password.of("password", passwordEncoder))
                .memberInfo(MemberInfo.builder()
                        .email(Email.of("test@naver.com"))
                        .passport(Passport.of(""))
                        .build());
    }
}
