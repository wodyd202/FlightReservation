package com.ljy.flightreservation.user.query.infrastructure;

import com.ljy.flightreservation.user.query.application.QueryUserRepository;
import com.ljy.flightreservation.user.query.domain.QueryUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.flightreservation.user.query.domain.QQueryUser.queryUser;

@Repository
public class QuerydslQueryUserRepository implements QueryUserRepository {
    @PersistenceContext private EntityManager entityManager;
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<QueryUser> findByUserId(String id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(queryUser)
                                                    .where(eqUserId(id))
                                                    .fetchFirst());
    }

    @Override
    @Transactional
    public void save(QueryUser user) {
        if(entityManager.contains(user)){
            entityManager.merge(user);
        }else{
            entityManager.persist(user);
        }
    }

    private BooleanExpression eqUserId(String id){
        return queryUser.id.eq(id);
    }
}
