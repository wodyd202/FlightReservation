package com.ljy.flightreservation.reservation.command.application;

import com.ljy.flightreservation.reservation.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.reservation.command.domain.agg.Reservation;

import java.time.LocalDate;

public interface ReservationRepository {
    void findByFlightInfoCodeAndDepartureDate(FlightInfoCode flightInfoCode, LocalDate departureDate);
    void save(Reservation reservation);
}
