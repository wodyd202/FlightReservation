package com.ljy.flightreservation.user.command.domain.agg;

import com.ljy.flightreservation.user.command.application.PassportRepository;
import com.ljy.flightreservation.user.command.domain.exception.InvalidPassportException;
import com.ljy.flightreservation.user.command.domain.value.Passport;
import org.springframework.stereotype.Component;

@Component
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
