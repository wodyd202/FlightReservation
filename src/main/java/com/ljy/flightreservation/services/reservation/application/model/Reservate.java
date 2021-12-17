package com.ljy.flightreservation.services.reservation.application.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservate {
    private long flightInfoSeq;

    @NotBlank(message = "좌석 코드를 입력해주세요.")
    private String sitCode;
}
