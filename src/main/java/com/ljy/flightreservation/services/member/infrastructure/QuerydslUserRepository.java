package com.ljy.flightreservation.services.member.infrastructure;

import com.ljy.flightreservation.core.infrastructure.QuerydslRepository;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QuerydslUserRepository extends QuerydslRepository<Member> implements MemberRepository {

    @Override
    public Optional<Member> findById(MemberId id) {
        return null;
    }

}
