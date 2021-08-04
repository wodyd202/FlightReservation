package com.ljy.flightreservation.reservation.command.application.model;

import com.ljy.flightreservation.airplane.domain.value.SitType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
public class SitModel {
    private String code;
    private SitType type;
    private long extraPrice;
}
