package com.ljy.flightreservation.services.command.application;

import com.ljy.flightreservation.services.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.services.command.domain.agg.Reservation;

import java.time.LocalDate;

public interface ReservationRepository {
    void findByFlightInfoCodeAndDepartureDate(FlightInfoCode flightInfoCode, LocalDate departureDate);
    void save(Reservation reservation);
}
