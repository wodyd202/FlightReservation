package com.ljy.flightreservation.flightInfo.domain.agg;

import com.ljy.flightreservation.flightInfo.domain.infra.AirplaneCodeConverter;
import com.ljy.flightreservation.flightInfo.domain.infra.BasePriceConverter;
import com.ljy.flightreservation.flightInfo.domain.value.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class FlightInfo {

    @Id
    @GeneratedValue
    private Long seq;

    @Embedded
    private FlightDepartureArrivalArea area;

    @Embedded
    private FlightTimes times;

    @Convert(converter = BasePriceConverter.class)
    @Column(nullable = false)
    private BasePrice price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightInfoState state;

    @Convert(converter = AirplaneCodeConverter.class)
    @Column(nullable = false)
    private AirplaneCode airplaneCode;

    @Builder
    public FlightInfo(FlightDepartureArrivalArea area,
                      FlightTimes times,
                      BasePrice price,
                      AirplaneCode airplaneCode) {
        this.area = area;
        this.times = times;
        this.price = price;
        this.airplaneCode = airplaneCode;
        this.state = FlightInfoState.ABLED;
    }
}
