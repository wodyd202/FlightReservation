package com.ljy.flightreservation.user.domain.model;

import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.value.Passport;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapFrom(RegisterUserCommand userCommand){
        return User.builder()
                .id(new UserId(userCommand.getId()))
                .password(new Password(userCommand.getPassword(), passwordEncoder))
                .passport(new Passport(userCommand.getPassport()))
                .build();
    }
}
