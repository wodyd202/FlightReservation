package com.ljy.flightreservation.services.member;

import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.value.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberMapper {
    private final PasswordEncoder passwordEncoder;

    public Member mapFrom(RegisterMember registerMember) {
        return Member.builder()
                .id(MemberId.of(registerMember.getId()))
                .password(Password.of(registerMember.getPassword(), passwordEncoder))
                .memberInfo(MemberInfo.builder()
                        .email(Email.of(registerMember.getMemberInfo().getEmail()))
                        .passport(Passport.of(registerMember.getMemberInfo().getPassport() != null ? registerMember.getMemberInfo().getPassport() : ""))
                        .build())
                .build();
    }
}
