package com.ljy.flightreservation.user.domain.agg;

import com.ljy.flightreservation.user.application.UserCommandRepository;
import com.ljy.flightreservation.user.domain.exception.AlreadyExistUserException;
import com.ljy.flightreservation.user.domain.value.UserId;

import java.util.Optional;

public class RegisterUserValidator {
    private final UserCommandRepository userRepository;

    public RegisterUserValidator(UserCommandRepository userCommandRepository) {
        this.userRepository = userCommandRepository;
    }

    public void validation(UserId id) {
        Optional<User> findUser = userRepository.findByUserId(id);
        if(findUser.isPresent()){
            throw new AlreadyExistUserException("already exist id of user");
        }
    }
}
