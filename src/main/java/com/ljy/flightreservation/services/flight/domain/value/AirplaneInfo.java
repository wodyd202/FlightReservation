package com.ljy.flightreservation.services.flight.domain.value;

import com.ljy.flightreservation.services.flight.domain.model.AirplaneInfoModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AirplaneInfo {
    private String code;

    @Builder
    public AirplaneInfo(String code) {
        this.code = code;
    }

    public AirplaneInfoModel toModel() {
        return AirplaneInfoModel.builder()
                .code(code)
                .build();
    }
}
