package com.ljy.flightreservation.services.airplane.application.model;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeSitCodeInfo {
    private String lastRowSitCode;
    private int lastColSitNumber;
}
