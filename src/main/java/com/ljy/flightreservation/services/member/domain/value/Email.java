package com.ljy.flightreservation.services.member.domain.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode(of = "email")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
    private String email;

    public static Email of(String email){
        return new Email(email);
    }

    private Email(String email) {
        validation(email);
        this.email = email;
    }

    private static Pattern PATTEN = Pattern.compile("[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    private void validation(String email) {
        verifyNotEmptyEmail(email);
        if(!PATTEN.matcher(email).find()){
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private void verifyNotEmptyEmail(String email) {
        if(!StringUtils.hasText(email)){
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
    }

    public String get() {
        return email;
    }
}
