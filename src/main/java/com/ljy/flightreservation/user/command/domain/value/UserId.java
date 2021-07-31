package com.ljy.flightreservation.user.command.domain.value;

import com.ljy.flightreservation.user.command.domain.exception.InvalidUserIdException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode(of = "email")
public class UserId implements Serializable {
    private static Pattern PATTEN = Pattern.compile("^[0-9a-z]*$");
    private final String id;

    protected UserId(){id = null;}

    public UserId(String id) {
        verifyNotEmpty(id);
        validation(id);
        this.id = id;
    }

    private void verifyNotEmpty(String id) {
        if(!StringUtils.hasText(id)){
            throw new InvalidUserIdException("id must not be empty");
        }
    }

    private void validation(String id) {
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
