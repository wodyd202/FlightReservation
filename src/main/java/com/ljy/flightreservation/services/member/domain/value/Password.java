package com.ljy.flightreservation.services.member.domain.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode(of = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String password;

    public static Password of(String password, PasswordEncoder passwordEncoder){
        return new Password(password, passwordEncoder);
    }

    private Password(String password, PasswordEncoder passwordEncoder) {
        verifyValidation(password);
        this.password = passwordEncoder.encode(password);
    }

    private void verifyValidation(String password) {
        if (password.length() < 5 || password.length() > 15) {
            throw new IllegalArgumentException("비밀번호는 5자 이상 15자 이하로 입력해주세요.");
        }
    }

    public String get() {
        return password;
    }
}
