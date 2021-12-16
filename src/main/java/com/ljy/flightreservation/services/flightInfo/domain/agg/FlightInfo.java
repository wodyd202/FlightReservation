package com.ljy.flightreservation.services.flightInfo.domain.agg;

import com.ljy.flightreservation.services.flightInfo.domain.infra.AirplaneCodeConverter;
import com.ljy.flightreservation.services.flightInfo.domain.infra.BasePriceConverter;
import com.ljy.flightreservation.services.flightInfo.domain.value.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(indexes = {@Index(columnList = "departureArea"), @Index(columnList = "arrivalArea")})
public class FlightInfo {

    @EmbeddedId
    private FlightInfoCode code;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NeedPassport needPassport;

    @Convert(converter = AirplaneCodeConverter.class)
    @Column(nullable = false)
    private AirplaneCode airplaneCode;

    @Builder
    public FlightInfo(FlightDepartureArrivalArea area,
                      FlightTimes times,
                      BasePrice price,
                      AirplaneCode airplaneCode,
                      NeedPassport needPassport) {
        this.area = area;
        this.times = times;
        this.price = price;
        this.airplaneCode = airplaneCode;
        this.needPassport = needPassport;
    }

    public void registration(RegistrationFlightInfoValidator registrationFlightInfoValidator, FlightInfoCode code) {
        registrationFlightInfoValidator.validation(airplaneCode);
        this.code = code;
        state = FlightInfoState.ABLED;
    }
}
