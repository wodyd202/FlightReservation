package com.ljy.flightreservation.services.member.domain;

import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(MemberId id);
    Optional<MemberModel> findById(String memberId);
}
