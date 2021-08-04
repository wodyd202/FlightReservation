package com.ljy.flightreservation.reservation.command.infrastructure;

import com.ljy.flightreservation.reservation.command.application.BookerRepository;
import com.ljy.flightreservation.reservation.command.application.model.UserModel;
import com.ljy.flightreservation.user.query.domain.QQueryUser;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ljy.flightreservation.user.query.domain.QQueryUser.queryUser;

@Repository
@Transactional(readOnly = true)
public class QuerydslBookerRepository implements BookerRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserModel> findByBooker(String booker) {
        UserModel userModel = jpaQueryFactory.select(userModel())
                .from(queryUser)
                .where(eqSteate("CREATED"), eqId(booker))
                .fetchFirst();
        return Optional.ofNullable(userModel);
    }

    private BooleanExpression eqSteate(String state){
        return queryUser.userState.eq(state);
    }

    private BooleanExpression eqId(String id){
        return queryUser.id.eq(id);
    }

    private ConstructorExpression<UserModel> userModel() {
        return Projections.constructor(UserModel.class, queryUser.id, queryUser.passport, queryUser.money);
    }
}
