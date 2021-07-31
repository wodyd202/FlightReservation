package com.ljy.flightreservation.user.command.infrastructure;

import com.ljy.flightreservation.user.command.application.UserRepository;
import com.ljy.flightreservation.user.command.domain.agg.QUser;
import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.flightreservation.user.command.domain.agg.QUser.user;

@Repository
@Transactional
public class QuerydslUserRepository implements UserRepository {

    @PersistenceContext private EntityManager entityManager;
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(User user) {
        if(entityManager.contains(user)){
            entityManager.merge(user);
        }else{
            entityManager.persist(user);
        }
    }

    @Override
    public Optional<User> findByUserId(UserId id) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                                .where(user.id().eq(id))
                                .fetchFirst());
    }
}
