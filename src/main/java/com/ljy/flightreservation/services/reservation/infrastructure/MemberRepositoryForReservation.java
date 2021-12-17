package com.ljy.flightreservation.services.reservation.infrastructure;

import com.ljy.flightreservation.services.reservation.application.external.MemberInfo;
import com.ljy.flightreservation.services.reservation.application.external.MemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.flightreservation.services.member.domain.QMember.member;
import static com.querydsl.core.types.dsl.Expressions.asSimple;


@Repository
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberRepositoryForReservation implements MemberRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MemberInfo getMember(String id) {
        return jpaQueryFactory.select(Projections.constructor(MemberInfo.class,
                        asSimple(id),
                        member.money,
                        member.memberInfo().passport()))
                .from(member)
                .where(member.id().id.eq(id))
                .fetchFirst();
    }
}
