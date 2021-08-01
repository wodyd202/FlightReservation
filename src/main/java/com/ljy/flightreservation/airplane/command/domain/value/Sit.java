package com.ljy.flightreservation.airplane.command.domain.value;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Sit implements Comparable {
    private final String code;
    private final SitType type;

    protected Sit() {
        code = null;
        type = null;
    }

    public Sit(String code, SitType type) {
        this.code = code;
        this.type = type;
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
