package com.ljy.flightreservation.services.command.application;

import com.ljy.flightreservation.services.command.application.model.UserModel;

import java.util.Optional;

public interface BookerRepository {
    Optional<UserModel> findByBooker(String booker);
}
