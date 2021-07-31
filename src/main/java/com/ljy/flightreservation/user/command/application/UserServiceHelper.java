package com.ljy.flightreservation.user.command.application;

import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.exception.UserNotFoundException;
import com.ljy.flightreservation.user.command.domain.value.UserId;

public class UserServiceHelper {
    public static User findByUserId(UserRepository userRepo, UserId userId) {
        return userRepo.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
