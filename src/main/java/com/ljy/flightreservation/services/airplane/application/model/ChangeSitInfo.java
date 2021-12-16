package com.ljy.flightreservation.services.airplane.application.model;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeSitInfo {
    private ChangeSitCodeInfo sitCodeInfo;
    private ChangeSpecialSitInfo specialSitInfo;
    private ChangeBusinessSitInfo businessSitInfo;
    private ChangeIgnoreSitInfo ignoreSitInfo;
}
