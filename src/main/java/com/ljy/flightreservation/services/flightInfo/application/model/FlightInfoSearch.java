package com.ljy.flightreservation.services.flightInfo.application.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlightInfoSearch {
    private String departureArea;
    private String arrivalArea;

    @Min(value = 0, message = "page must be 0 or more")
    private int page;

    @Min(value = 0, message = "size must be 10 or more")
    @Max(value = 50, message = "size must be 50 or less")
    private int size;
}
