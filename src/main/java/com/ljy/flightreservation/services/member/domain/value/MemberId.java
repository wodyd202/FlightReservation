package com.ljy.flightreservation.services.member.domain.value;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberId implements Serializable {
    private String id;

    public static MemberId of(String id){
        return new MemberId(id);
    }

    private MemberId(String id) {
        validation(id);
        this.id = id;
    }

    private static Pattern PATTEN = Pattern.compile("^[0-9a-z]{7,15}$");
    private void validation(String id) {
        verifyNotEmpty(id);
        if(!PATTEN.matcher(id).find()){
            throw new IllegalArgumentException("아이디는 [영어 소문자, 숫자] 조합으로 7자 이상 15자 이하로 입력해주세요.");
        }
    }

    private void verifyNotEmpty(String id) {
        if(!StringUtils.hasText(id)){
            throw new IllegalArgumentException("사용자 아이디를 입력해주세요.");
        }
    }

    public String get() {
        return id;
    }
}
