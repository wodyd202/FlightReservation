package com.ljy.flightreservation.user.command.infrastructure;

import com.ljy.flightreservation.core.infrastructure.QuerydslRepository;
import com.ljy.flightreservation.user.command.application.UserRepository;
import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.flightreservation.user.command.domain.agg.QUser.user;

@Repository
public class QuerydslUserRepository extends QuerydslRepository<User> implements UserRepository {

    @Override
    public Optional<User> findByUserId(UserId id) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                                .where(user.id().eq(id))
                                .fetchFirst());
    }

}
