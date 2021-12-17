package com.ljy.flightreservation.services.reservation.domain.model;

import com.ljy.flightreservation.services.reservation.domain.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationModel {
    private Long seq;
    private FlightInfoModel flightInfo;
    private long price;
    private ReservationState state;
    private LocalDate reservationDate;
    private String sitCode;
    private String booker;

    @Builder
    public ReservationModel(Long seq,
                            FlightInfo flightInfo,
                            TotalPrice price,
                            ReservationState state,
                            LocalDate reservationDate,
                            SitInfo sitCode,
                            Booker booker) {
        this.seq = seq;
        this.flightInfo = flightInfo.toModel();
        this.price = price.get();
        this.state = state;
        this.reservationDate = reservationDate;
        this.sitCode = sitCode.getCode();
        this.booker = booker.getId();
    }
}
