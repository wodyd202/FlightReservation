package com.ljy.flightreservation.services.flight.domain;

import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import com.ljy.flightreservation.services.flight.domain.value.infra.BasePriceConverter;
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

    // 기본 가격
    @Convert(converter = BasePriceConverter.class)
    private BasePrice basePrice;

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
    public Flight(BasePrice basePrice,
                  AirplaneInfo airplaneInfo,
                  FlightDetail flightDetail,
                  NeedPassport needPassport) {
        this.basePrice = basePrice;
        this.airplaneInfo = airplaneInfo;
        this.flightDetail = flightDetail;
        this.needPassport = needPassport;
    }

    public FlightModel toModel() {
        return FlightModel.builder()
                .seq(seq)
                .basePrice(basePrice)
                .flightDetail(flightDetail)
                .airplaneInfo(airplaneInfo)
                .needPassport(needPassport)
                .state(state)
                .build();
    }

    public void register(RegisterFlightValidator registerFlightValidator) {
        registerFlightValidator.validation(airplaneInfo);
        state = FlightState.IN_OPER;
    }
}
