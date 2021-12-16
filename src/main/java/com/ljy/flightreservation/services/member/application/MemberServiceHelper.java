package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.exception.UserNotFoundException;
import com.ljy.flightreservation.services.member.domain.value.MemberId;

public class MemberServiceHelper {
    public static Member findByUserId(MemberRepository userRepo, MemberId userId) {
        return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
