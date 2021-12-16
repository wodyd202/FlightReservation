package com.ljy.flightreservation.services.command.application.model;

import com.ljy.flightreservation.services.airplane.domain.value.SitType;
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
