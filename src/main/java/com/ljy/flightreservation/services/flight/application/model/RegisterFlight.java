package com.ljy.flightreservation.services.flight.application.model;

import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterFlight {
    private String airplaneCode;
    private ChangeFlightDetail flightDetail;
    private NeedPassport needPassport;
}
