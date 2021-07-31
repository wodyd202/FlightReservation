package com.ljy.flightreservation.user.command.infrastructure;

import com.ljy.flightreservation.user.command.application.PassportRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StubPassportRepository implements PassportRepository {
    @Override
    public boolean checkPassport(String passport) {
        return true;
    }
}
