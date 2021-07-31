package com.ljy.flightreservation.user.domain.agg;


import com.ljy.flightreservation.user.domain.exception.AlreadyDeletedUserException;
import com.ljy.flightreservation.user.domain.exception.InvalidEmailException;
import com.ljy.flightreservation.user.domain.exception.InvalidPasswordException;
import com.ljy.flightreservation.user.domain.exception.InvalidUserIdException;
import com.ljy.flightreservation.user.domain.value.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class User {
    private UserId id;
    private Password password;
    private Email email;
    private Passport passport;
    private Money money;
    private UserState state;
    private LocalDateTime createDateTime;

    public void register(RegisterUserValidator registerUserValidator) {
        registerUserValidator.validation(id, passport);
        this.state = UserState.CREATED;
        this.createDateTime = LocalDateTime.now();
    }

    public void changePassword(String inputOriginPassword,
                               Password changePassword,
                               PasswordEncoder passwordEncoder) {
        verifyEqualsOriginPassword(inputOriginPassword, passwordEncoder);
        this.password = changePassword;
    }

    public void withdrawal(String inputOriginPassword, PasswordEncoder passwordEncoder) {
        verifyCreatedUser();
        verifyEqualsOriginPassword(inputOriginPassword, passwordEncoder);
        this.state = UserState.DELETED;
    }

    private void verifyEqualsOriginPassword(String inputOriginPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(inputOriginPassword, password.get())) {
            throw new InvalidPasswordException("not equals origin password");
        }
    }

    public void changePassport(PassportValidator changePassportValidator,
                               Passport passport) {
        verifyCreatedUser();
        changePassportValidator.validation(passport);
        this.passport = passport;
    }

    public void temporaryPassword(Password password) {
        verifyCreatedUser();
        this.password = password;
    }

    public void deposit(long won) {
        verifyCreatedUser();
        this.money = this.money.plus(won);
    }

    public void pay(long won) {
        verifyCreatedUser();
        this.money = this.money.minus(won);
    }

    private void verifyCreatedUser() {
        if (Objects.isNull(state)) {
            return;
        }
        if (!state.isCreated()) {
            throw new AlreadyDeletedUserException("alraedy deleted user");
        }
    }

    @Builder
    public User(UserId id, Password password, Email email, Passport passport) {
        verifyNotNullId(id);
        verifyNotNullPassword(password);
        verifyNotNullEmail(email);
        this.id = id;
        this.password = password;
        this.email = email;
        this.passport = passport;
        this.money = Money.won(0);
    }

    private void verifyNotNullId(UserId id) {
        if (Objects.isNull(id)) {
            throw new InvalidUserIdException("id must not be null");
        }
    }

    private void verifyNotNullPassword(Password password) {
        if (Objects.isNull(password)) {
            throw new InvalidPasswordException("password must not be null");
        }
    }

    private void verifyNotNullEmail(Email email) {
        if (Objects.isNull(email)) {
            throw new InvalidEmailException("id must not be null");
        }
    }
}
