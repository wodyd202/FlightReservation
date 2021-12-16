package com.ljy.flightreservation.services.member;

import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.value.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MemberMapper {
    private PasswordEncoder passwordEncoder;

    public Member mapFrom(RegisterMember registerMember) {
        return Member.builder()
                .id(MemberId.of(registerMember.getId()))
                .password(Password.of(registerMember.getPassword(), passwordEncoder))
                .memberInfo(MemberInfo.builder()
                        .email(Email.of(registerMember.getMemberInfo().getEmail()))
                        .passport(Passport.of(registerMember.getMemberInfo().getPassport()))
                        .build())
                .build();
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}

