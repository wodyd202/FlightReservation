package com.ljy.flightreservation.user.command.domain.value;

import com.ljy.flightreservation.user.command.domain.exception.InvalidEmailException;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode(of = "email")
public class Email {
    private static Pattern PATTEN = Pattern.compile("[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    private final String email;

    protected Email(){email = null;}

    public Email(String email) {
        verifyNotEmptyEmail(email);
        validation(email);
        this.email = email;
    }

    private void verifyNotEmptyEmail(String email) {
        if(!StringUtils.hasText(email)){
            throw new InvalidEmailException("email must not be empty");
        }
    }

    private void validation(String email) {
        if(!PATTEN.matcher(email).find()){
            throw new InvalidEmailException("invalid email");
        }
    }

    public String get() {
        return email;
    }
}
