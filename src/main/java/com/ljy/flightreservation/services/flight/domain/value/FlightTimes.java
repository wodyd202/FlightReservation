package com.ljy.flightreservation.services.flight.domain.value;

import com.ljy.flightreservation.services.flight.domain.exception.InvalidFightInfoException;
import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightTimes {
    private int departureHour;
    private int departureMinute;
    private int arrivalHour;
    private int arrivalMinute;

    @Builder
    public FlightTimes(int departureHour, int departureMinute, int arrivalHour, int arrivalMinute) {
        flightTimesValidation(departureHour, arrivalHour);
        this.departureHour = departureHour;
        this.departureMinute = departureMinute;
        this.arrivalHour = arrivalHour;
        this.arrivalMinute = arrivalMinute;
    }

    private void flightTimesValidation(int departureHour, int arrivalHour) {
        if(departureHour > arrivalHour){
            throw new InvalidFightInfoException("invalid flight times");
        }
        if(departureHour == arrivalHour && departureHour >= arrivalHour){
            throw new InvalidFightInfoException("invalid flight times");
        }
    }
}
