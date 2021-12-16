package com.ljy.flightreservation.services.airplane.domain.model;

import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneState;
import com.ljy.flightreservation.services.airplane.domain.value.SitInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AirplaneModel {
    private String code;
    private SitInfoModel sitInfo;
    private AirplaneState state;

    @Builder
    public AirplaneModel(AirplaneCode code, SitInfo sitInfo, AirplaneState state) {
        this.code = code.get();
        this.sitInfo = sitInfo.toModel();
        this.state = state;
    }
}
