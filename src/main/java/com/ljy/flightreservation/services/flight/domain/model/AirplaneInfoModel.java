package com.ljy.flightreservation.services.flight.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AirplaneInfoModel {
    private String code;

    @Builder
    public AirplaneInfoModel(String code) {
        this.code = code;
    }
}
