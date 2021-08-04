package com.ljy.flightreservation.airplane.application.model;

import com.ljy.flightreservation.airplane.domain.value.Sit;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationAirplane {
    private List<Integer> corridorIndexes;
    private List<Sit> sits;
}
