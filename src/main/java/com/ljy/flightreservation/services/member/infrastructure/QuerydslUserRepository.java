package com.ljy.flightreservation.services.member.infrastructure;

import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.flightreservation.services.member.domain.QMember.member;

@Repository
@Transactional
@AllArgsConstructor
public class QuerydslUserRepository implements MemberRepository {
    @PersistenceContext private EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<MemberModel> findById(String memberId) {
        return Optional.ofNullable(jpaQueryFactory.select(Projections.constructor(MemberModel.class,
                        member.id(),
                        member.password(),
                        member.memberInfo(),
                        member.money,
                        member.state,
                        member.createDateTime))
                .from(member)
                .where(member.id().id.eq(memberId))
                .fetchFirst());
    }

    @Override
    public Optional<Member> findById(MemberId id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member).where(member.id().eq(id)).fetchFirst());
    }

    @Override
    public void save(Member member) {
        entityManager.persist(member);
    }
}
