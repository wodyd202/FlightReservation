package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.exception.UserNotFoundException;
import com.ljy.flightreservation.services.member.domain.value.MemberId;

public class MemberServiceHelper {
    public static Member getMember(MemberRepository memberRepository, MemberId userId) {
        return memberRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
