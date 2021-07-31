package com.ljy.flightreservation.user.command.application.model;

import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.value.Email;
import com.ljy.flightreservation.user.command.domain.value.Passport;
import com.ljy.flightreservation.user.command.domain.value.Password;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapFrom(RegisterUser userCommand){
        return User.builder()
                .id(new UserId(userCommand.getId()))
                .password(new Password(userCommand.getPassword(), passwordEncoder))
                .email(new Email(userCommand.getEmail()))
                .passport(new Passport(userCommand.getPassport()))
                .build();
    }
}
