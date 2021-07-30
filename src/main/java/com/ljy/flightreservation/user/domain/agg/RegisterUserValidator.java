package com.ljy.flightreservation.user.domain.agg;

import com.ljy.flightreservation.user.application.PassportRepository;
import com.ljy.flightreservation.user.application.UserCommandRepository;
import com.ljy.flightreservation.user.domain.exception.AlreadyExistUserException;
import com.ljy.flightreservation.user.domain.exception.InvalidPassportException;
import com.ljy.flightreservation.user.domain.value.Passport;
import com.ljy.flightreservation.user.domain.value.UserId;

import java.util.Objects;
import java.util.Optional;

public class RegisterUserValidator {
    private final UserCommandRepository userRepository;
    private final ChangePassportValidator passportValidator;

    public RegisterUserValidator(UserCommandRepository userCommandRepository, ChangePassportValidator passportValidator) {
        this.userRepository = userCommandRepository;
        this.passportValidator = passportValidator;
    }

    public void validation(UserId id, Passport passport) {
        Optional<User> findUser = getUser(id);
        if(findUser.isPresent()){
            throw new AlreadyExistUserException("already exist id of user");
        }
        if(!Objects.isNull(passport) && !passport.isEmpty()){
            passportValidator.validation(passport);
        }
    }

    private Optional<User> getUser(UserId id) {
        return userRepository.findByUserId(id);
    }
}
