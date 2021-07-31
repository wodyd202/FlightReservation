package com.ljy.flightreservation.user;

import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.value.Email;
import com.ljy.flightreservation.user.command.domain.value.Password;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

abstract  public class UserFixture {
    public static User.UserBuilder aUser(){
        return User.builder()
                    .id(new UserId("userid"))
                    .email(new Email("test@test.com"))
                    .password(new Password("password", createDelegatingPasswordEncoder()));
    }

    public static User aDeletedUser(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        Password password = new Password("password", passwordEncoder);
        User user = aUser().password(password).build();
        user.withdrawal("password", passwordEncoder);
        return user;
    }
}
