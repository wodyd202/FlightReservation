package com.ljy.flightreservation.services.reservation.application.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservate {
    private long flightInfoSeq;
    private String sitCode;
}
