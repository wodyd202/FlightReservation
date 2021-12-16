package com.ljy.flightreservation.services.airplane.domain;

import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneState;
import com.ljy.flightreservation.services.airplane.domain.value.SitInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 항공기
 */
@Entity
@Table(name = "airplanes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airplane {
    // 항공기 코드
    @EmbeddedId
    private AirplaneCode code;

    // 좌석 정보
    @Embedded
    private SitInfo sitInfo;

    // 항공기 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirplaneState state;

    @Builder
    public Airplane(AirplaneCode code, SitInfo sitInfo) {
        this.code = code;
        this.sitInfo = sitInfo;
    }

    public void register(RegisterAirplaneValidator validator){
        validator.validation(code);
        state = AirplaneState.IN_FLIGHT;
    }

    public AirplaneModel toModel() {
        return AirplaneModel.builder()
                .code(code)
                .sitInfo(sitInfo)
                .state(state)
                .build();
    }
}
