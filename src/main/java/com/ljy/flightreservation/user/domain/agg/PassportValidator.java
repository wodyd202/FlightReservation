package com.ljy.flightreservation.user.domain.agg;

import com.ljy.flightreservation.user.application.PassportRepository;
import com.ljy.flightreservation.user.domain.exception.InvalidPassportException;
import com.ljy.flightreservation.user.domain.value.Passport;

public class PassportValidator {
    private final PassportRepository passportRepository;

    public PassportValidator(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public void validation(Passport passport){
        if(!passportRepository.checkPassport(passport.get())){
            throw new InvalidPassportException("invalid passport");
        }
    }
}
