package com.ljy.flightreservation.user.query.application;

import com.ljy.flightreservation.user.query.domain.QueryUser;

import java.util.Optional;

public interface QueryUserRepository {
    Optional<QueryUser> findByUserId(String id);
    void save(QueryUser user);
}
