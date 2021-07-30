package com.ljy.flightreservation.user.domain.agg;

import com.ljy.flightreservation.user.application.PassportRepository;
import com.ljy.flightreservation.user.domain.exception.InvalidPassportException;
import com.ljy.flightreservation.user.domain.value.Passport;

public class ChangePassportValidator {
    private final PassportRepository passportRepository;

    public ChangePassportValidator(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public void validation(Passport passport){
        if(!passportRepository.checkPassport(passport.get())){
            throw new InvalidPassportException("invalid passport");
        }
    }
}
