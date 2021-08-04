package com.ljy.flightreservation.reservation.command.application;

import com.ljy.flightreservation.reservation.command.application.model.UserModel;

import java.util.Optional;

public interface BookerRepository {
    Optional<UserModel> findByBooker(String booker);
}
