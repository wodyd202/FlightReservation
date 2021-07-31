package com.ljy.flightreservation.user.command.domain.value;

import com.ljy.flightreservation.user.command.domain.exception.InvalidPasswordException;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode(of = "password")
public class Password {
    private final String password;

    protected Password(){password = null;}

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