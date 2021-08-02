package com.ljy.flightreservation.airplane.domain.value;

import com.ljy.flightreservation.airplane.InvalidSitException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@EqualsAndHashCode(of = "code")
public class Sit implements Comparable {
    private final String code;
    private final SitType type;
    private final long extraPrice;

    protected Sit() {
        code = null;
        type = null;
        extraPrice = 0;
    }

    public Sit(String code, SitType type, long extraPrice) {
        this.code = code;
        this.type = type;
        if(!type.equals(SitType.NOMAL) && extraPrice == 0){
            throw new InvalidSitException("invalid extra price");
        }
        this.extraPrice = extraPrice;
    }

    @Override
    public int compareTo(Object o) {
        char keyCode = ((Sit) o).code.charAt(0);
        int numberCode = Integer.parseInt(((Sit) o).code.substring(1, ((Sit) o).code.length()));

        char thisKeyCode = code.charAt(0);
        int thisNumberCode = Integer.parseInt(code.substring(1, code.length()));

        return (thisKeyCode - keyCode) - (numberCode - thisNumberCode);
    }
}
