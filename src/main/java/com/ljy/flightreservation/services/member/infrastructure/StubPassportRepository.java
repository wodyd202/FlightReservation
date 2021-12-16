package com.ljy.flightreservation.services.member.infrastructure;

import com.ljy.flightreservation.services.member.domain.PassportRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StubPassportRepository implements PassportRepository {
    @Override
    public boolean checkPassport(String passport) {
        return !passport.equals("a10382738");
    }
}
