package com.ljy.flightreservation.reservation;

import com.ljy.flightreservation.reservation.command.domain.value.Booker;
import com.ljy.flightreservation.reservation.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.reservation.command.domain.agg.Reservation;
import com.ljy.flightreservation.reservation.command.domain.value.SitCodes;

import java.util.Arrays;

public class ReservationFixture {
    public static Reservation.ReservationBuilder aReservation(){
        return Reservation.builder()
                .booker(new Booker("booker"))
                .flightInfoCode(new FlightInfoCode("flightInfoCode"))
                .sitCodes(new SitCodes(Arrays.asList("A0", "B10")));
    }
}
