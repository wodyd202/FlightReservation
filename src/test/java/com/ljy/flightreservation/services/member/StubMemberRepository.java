package com.ljy.flightreservation.services.member;

import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;

import java.util.HashMap;
import java.util.Optional;

public class StubMemberRepository implements MemberRepository {
    private final HashMap<String, Member> repo = new HashMap<>();

    @Override
    public void save(Member member) {
        MemberModel memberModel = member.toModel();
        repo.put(memberModel.getId(), member);
    }

    @Override
    public Optional<Member> findById(MemberId id) {
        Member member = repo.get(id.get());
        return Optional.ofNullable(member);
    }
}
