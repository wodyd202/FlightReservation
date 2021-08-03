package com.ljy.flightreservation.user.query.infrastructure;

import com.ljy.flightreservation.core.infrastructure.QuerydslRepository;
import com.ljy.flightreservation.user.query.application.QueryUserRepository;
import com.ljy.flightreservation.user.query.domain.QueryUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.flightreservation.user.query.domain.QQueryUser.queryUser;

@Repository
public class QuerydslQueryUserRepository extends QuerydslRepository<QueryUser> implements QueryUserRepository {

    @Override
    public Optional<QueryUser> findByUserId(String id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(queryUser)
                                                    .where(eqUserId(id))
                                                    .fetchFirst());
    }

    private BooleanExpression eqUserId(String id){
        return queryUser.id.eq(id);
    }
}
