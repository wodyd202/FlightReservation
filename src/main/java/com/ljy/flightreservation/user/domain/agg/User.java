package com.ljy.flightreservation.user.domain.agg;


import com.ljy.flightreservation.user.domain.exception.InvalidPasswordException;
import com.ljy.flightreservation.user.domain.exception.InvalidUserIdException;
import com.ljy.flightreservation.user.domain.value.Passport;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;
import com.ljy.flightreservation.user.domain.value.UserState;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class User {
    private UserId id;
    private Password password;
    private UserState state;
    private Passport passport;
    private LocalDateTime createDateTime;

    public void register(RegisterUserValidator registerUserValidator) {
        registerUserValidator.validation(id, passport);
        this.state = UserState.CREATED;
        this.createDateTime = LocalDateTime.now();
    }

    public void changePassword(String originPassword,
                               String changePassword,
                               PasswordEncoder passwordEncoder) {
        verifyEqualsOriginPassword(originPassword, passwordEncoder);
        this.password = new Password(changePassword, passwordEncoder);
    }

    public void withdrawal(String originPassword,
                           PasswordEncoder passwordEncoder) {
        verifyEqualsOriginPassword(originPassword, passwordEncoder);
        this.state = UserState.DELETED;
    }

    private void verifyEqualsOriginPassword(String originPassword, PasswordEncoder passwordEncoder) {
        if(!password.equalsOriginPassword(originPassword, passwordEncoder)){
            throw new InvalidPasswordException("not equals origin password");
        }
    }

    @Builder
    public User(UserId id, Password password) {
        verifyNotNullId(id);
        verifyNotNullPassword(password);
        this.id = id;
        this.password = password;
    }

    private void verifyNotNullId(UserId id) {
        if(Objects.isNull(id)){
            throw new InvalidUserIdException("id must not be null");
        }
    }

    private void verifyNotNullPassword(Password password) {
        if(Objects.isNull(password)){
            throw new InvalidPasswordException("password must not be null");
        }
    }

}
