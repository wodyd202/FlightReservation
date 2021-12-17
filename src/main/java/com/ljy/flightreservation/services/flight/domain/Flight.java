package com.ljy.flightreservation.services.flight.domain;

import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 운항 정보
 */
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight {

    // 운항 정보 고유값
    @Id
    @GeneratedValue
    private Long seq;

    // 비행기 정보
    @Embedded
    private AirplaneInfo airplaneInfo;

    // 운항 상세 정보
    @Embedded
    private FlightDetail flightDetail;

    // 여권 필요 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NeedPassport needPassport;

    // 운항 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightState state;

    @Builder
    public Flight(AirplaneInfo airplaneInfo,
                  FlightDetail flightDetail,
                  NeedPassport needPassport,
                  FlightState state) {
        this.airplaneInfo = airplaneInfo;
        this.flightDetail = flightDetail;
        this.needPassport = needPassport;
        this.state = state;
    }

    public FlightModel toModel() {
        return FlightModel.builder()
                .seq(seq)
                .flightDetail(flightDetail.toModel())
                .airplaneInfo(airplaneInfo.toModel())
                .needPassport(needPassport)
                .state(state)
                .build();
    }
}
