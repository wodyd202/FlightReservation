package com.ljy.flightreservation.services.reservation;

import com.ljy.flightreservation.services.command.domain.value.Booker;
import com.ljy.flightreservation.services.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.services.command.domain.agg.Reservation;
import com.ljy.flightreservation.services.command.domain.value.SitCodes;

import java.util.Arrays;

public class ReservationFixture {
    public static Reservation.ReservationBuilder aReservation(){
        return Reservation.builder()
                .booker(new Booker("booker"))
                .flightInfoCode(new FlightInfoCode("flightInfoCode"))
                .sitCodes(new SitCodes(Arrays.asList("A0", "B10")));
    }
}
