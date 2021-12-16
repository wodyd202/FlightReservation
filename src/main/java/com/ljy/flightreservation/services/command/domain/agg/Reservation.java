package com.ljy.flightreservation.services.command.domain.agg;

import com.ljy.flightreservation.services.command.domain.value.Price;
import com.ljy.flightreservation.services.command.domain.value.ReservationCode;
import com.ljy.flightreservation.services.command.domain.value.Booker;
import com.ljy.flightreservation.services.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.services.command.domain.value.ReservationState;
import com.ljy.flightreservation.services.command.domain.value.SitCodes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    private ReservationCode code;
    private LocalDateTime createDateTime;
    private Price totalPrice;
    private ReservationState state;

    private Booker booker;
    private FlightInfoCode flightInfoCode;
    private SitCodes sitCodes;
    private LocalDate reservationDate;

//    public void request(ReservationCode code, ReservationRequestValidator reservationRequestValidator) {
//        long totalPrice = reservationRequestValidator.validationAfterReturnTotalPrice(booker, flightInfoCode, sitCodes, reservationDate);
//        this.code = code;
//        this.createDateTime = LocalDateTime.now();
//        this.state = ReservationState.SUCCESS;
//        this.totalPrice = Price.won(totalPrice);
//    }

    @Builder
    public Reservation(Booker booker,
                       FlightInfoCode flightInfoCode,
                       SitCodes sitCodes,
                       LocalDate reservationDate) {
        this.booker = booker;
        this.flightInfoCode = flightInfoCode;
        this.sitCodes = sitCodes;
        this.reservationDate = reservationDate;
    }

}
