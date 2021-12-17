package com.ljy.flightreservation.services.reservation.domain.value;

import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.flight.domain.value.NeedPassport;
import com.ljy.flightreservation.services.reservation.application.PassportValidator;
import com.ljy.flightreservation.services.reservation.application.external.MemberInfo;
import com.ljy.flightreservation.services.reservation.domain.model.FlightInfoModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightInfo {
    // 운항 고유 번호
    private long seq;

    @Transient
    private long basePrice;

    @Transient
    private String airplaneCode;

    @Transient
    private NeedPassport needPassport;

    private LocalDate departureDate;
    private int departureTime;

    private LocalDate arrivalDate;
    private int estimatedArrivalTime;

    private String arrivalArea;

    @Builder
    public FlightInfo(long seq,
                      BasePrice basePrice,
                      NeedPassport needPassport,
                      String airplaneCode,
                      LocalDate departureDate,
                      int departureTime,
                      LocalDate arrivalDate,
                      int estimatedArrivalTime,
                      String arrivalArea) {
        this.seq = seq;
        this.needPassport = needPassport;
        this.basePrice = basePrice.get();
        this.airplaneCode = airplaneCode;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.arrivalArea = arrivalArea;
    }

    public FlightInfoModel toModel() {
        return FlightInfoModel.builder()
                .seq(seq)
                .arrivalArea(arrivalArea)
                .arrivalDate(arrivalDate)
                .departureDate(departureDate)
                .departureTime(departureTime)
                .estimatedArrivalTime(estimatedArrivalTime)
                .build();
    }

    public boolean ableReservate(MemberInfo member, PassportValidator passportValidator) {
        if(needPassport.equals(NeedPassport.NO)){
            return true;
        }
        if(member.getPassport() == null){
            return false;
        }
        passportValidator.validation(member.getPassport());
        return true;
    }
}
