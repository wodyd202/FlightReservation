package com.ljy.flightreservation.services.flightInfo.domain.value;

import com.ljy.flightreservation.services.flightInfo.domain.exception.InvalildFlightInfoException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightDepartureArrivalArea {
    private String departureArea;
    private String arrivalArea;

    @Builder
    public FlightDepartureArrivalArea(String departureArea, String arrivalArea) {
        verifyNotEmptyDepartureInfo(departureArea);
        verifyNotEmptyArrivalInfo(arrivalArea);

        this.departureArea = departureArea;
        this.arrivalArea = arrivalArea;
    }

    private void verifyNotEmptyDepartureInfo(String departureArea) {
        if(!StringUtils.hasText(departureArea)){
            throw new InvalildFlightInfoException("departure area must not be empty");
        }
    }

    private void verifyNotEmptyArrivalInfo(String arrivalArea) {
        if(!StringUtils.hasText(arrivalArea)){
            throw new InvalildFlightInfoException("arrival area must not be empty");
        }
    }
}
