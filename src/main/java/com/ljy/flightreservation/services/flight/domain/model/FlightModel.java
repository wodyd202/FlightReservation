package com.ljy.flightreservation.services.flight.domain.model;


import com.ljy.flightreservation.services.flight.domain.AirplaneInfo;
import com.ljy.flightreservation.services.flight.domain.FlightDetail;
import com.ljy.flightreservation.services.flight.domain.FlightState;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightModel {
    private Long seq;
    private long basePrice;
    private AirplaneInfoModel airplaneInfo;
    private FlightDetailModel flightDetail;
    private NeedPassport needPassport;
    private FlightState state;

    @Builder
    public FlightModel(Long seq,
                       BasePrice basePrice,
                       AirplaneInfo airplaneInfo,
                       FlightDetail flightDetail,
                       NeedPassport needPassport,
                       FlightState state) {
        this(seq, basePrice, flightDetail, needPassport, state);
        this.airplaneInfo = airplaneInfo.toModel();
    }

    public FlightModel(Long seq,
                       BasePrice basePrice,
                       FlightDetail flightDetail,
                       NeedPassport needPassport,
                       FlightState state) {
        this.seq = seq;
        this.basePrice = basePrice.get();
        this.flightDetail = flightDetail.toModel();
        this.needPassport = needPassport;
        this.state = state;
    }
}
