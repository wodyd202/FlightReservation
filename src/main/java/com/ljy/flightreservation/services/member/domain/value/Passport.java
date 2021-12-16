package com.ljy.flightreservation.services.member.domain.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode(of = "passport")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Passport {
    private String passport;

    public Passport(String passport) {
        if(!StringUtils.hasText(passport)) {
            return;
        }
        validation(passport);
        this.passport = passport;
    }

    private static Pattern PATTEN = Pattern.compile("([a-zA-Z]{1}|[a-zA-Z]{2})\\d{8}");

    public static Passport of(String passport) {
        return new Passport(passport);
    }

    private void validation(String passport) {
        if(!PATTEN.matcher(passport).find()){
            throw new IllegalArgumentException("여권 번호 형식이 유효하지 않습니다.");
        }
    }

    public String get() {
        return passport;
    }
}
