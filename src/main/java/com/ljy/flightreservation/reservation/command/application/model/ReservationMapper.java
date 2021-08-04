package com.ljy.flightreservation.reservation.command.application.model;

import com.ljy.flightreservation.reservation.command.domain.value.Booker;
import com.ljy.flightreservation.reservation.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.reservation.command.domain.agg.Reservation;
import com.ljy.flightreservation.reservation.command.domain.value.ReservationCode;
import com.ljy.flightreservation.reservation.command.domain.value.SitCodes;

final public class ReservationMapper {
    public Reservation mapFrom(ReservationCode reservationCode,
                               CreateReservation reservationCommand,
                               String booker){
        return Reservation.builder()
                .booker(new Booker(booker))
                .flightInfoCode(new FlightInfoCode(reservationCommand.getFlightInfoCode()))
                .sitCodes(new SitCodes(reservationCommand.getReservationSits()))
                .build();
    }
}
