package com.ljy.flightreservation.user.application;

import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.value.UserId;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UserId id);
}
