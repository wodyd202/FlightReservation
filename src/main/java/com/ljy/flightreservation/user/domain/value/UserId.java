package com.ljy.flightreservation.user.domain.value;

import com.ljy.flightreservation.user.domain.exception.InvalidUserIdException;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@EqualsAndHashCode(of = "email")
public class UserId {
    private static Pattern PATTEN = Pattern.compile("^[0-9a-z]*$");
    private final String id;

    public UserId(String id) {
        verifyNotEmpty(id);
        verifyValidation(id);
        this.id = id;
    }

    private void verifyNotEmpty(String id) {
        if(!StringUtils.hasText(id)){
            throw new InvalidUserIdException("id must not be empty");
        }
    }

    private void verifyValidation(String id) {
        if(!PATTEN.matcher(id).find()){
            throw new InvalidUserIdException("invalid id");
        }
        if(id.length() < 4 || id.length() > 15){
            throw new InvalidUserIdException("id length must be 4 or more and 15 or less");
        }
    }

    public String get() {
        return id;
    }
}
