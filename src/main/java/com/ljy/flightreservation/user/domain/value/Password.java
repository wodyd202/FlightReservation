package com.ljy.flightreservation.user.domain.value;

import com.ljy.flightreservation.user.domain.exception.InvalidPasswordException;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.password.PasswordEncoder;

@EqualsAndHashCode(of = "password")
public class Password {
    private final String password;

    public Password(String password, PasswordEncoder passwordEncoder) {
        verifyValidation(password);
        this.password = passwordEncoder.encode(password);
    }

    private void verifyValidation(String password) {
        if (password.length() < 5 || password.length() > 15) {
            throw new InvalidPasswordException("password size must be 5 or more and 15 or less");
        }
    }

    public String get() {
        return password;
    }
}
