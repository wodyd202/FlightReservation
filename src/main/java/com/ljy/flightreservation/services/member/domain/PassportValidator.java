package com.ljy.flightreservation.services.member.domain;

import com.ljy.flightreservation.services.member.domain.value.Passport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 여권번호 validator
 */
@Component
@AllArgsConstructor
public class PassportValidator {
    private final PassportRepository passportRepository;

    public void validation(Passport passport){
        if(!passportRepository.checkPassport(passport.get())){
            throw new IllegalArgumentException("여권번호가 유효하지 않습니다.");
        }
    }
}
