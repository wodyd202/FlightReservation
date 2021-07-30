package com.ljy.flightreservation.user.domain.value;

import com.ljy.flightreservation.user.domain.exception.InvalidPassportException;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@EqualsAndHashCode(of = "passport")
public class Passport {
    private static Pattern PATTEN = Pattern.compile("([a-zA-Z]{1}|[a-zA-Z]{2})\\d{8}");
    private String passport;
    public Passport(String passport) {
        if(!StringUtils.hasText(passport)) {
            return;
        }
        if(!PATTEN.matcher(passport).find()){
            throw new InvalidPassportException("invalid passport");
        }
        this.passport = passport;
    }

    public String get() {
        return passport;
    }

    public boolean isEmpty() {
        return !StringUtils.hasText(passport);
    }
}
