package com.ljy.flightreservation.services.reservation.infrastructure;

import com.ljy.flightreservation.services.member.domain.QMember;
import com.ljy.flightreservation.services.reservation.application.external.MemberRepository;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
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
    public Booker getMember(String id) {
        return jpaQueryFactory.select(Projections.constructor(Booker.class,
                        asSimple(id),
                        member.money))
                .from(member)
                .where(member.id().id.eq(id))
                .fetchFirst();
    }
}
