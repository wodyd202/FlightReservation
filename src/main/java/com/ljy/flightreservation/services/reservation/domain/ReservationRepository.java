package com.ljy.flightreservation.services.reservation.domain;

import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;

import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);
    Optional<Reservation> findByFlightSeqAndSitCode(long seq, SitInfo sitInfo);
}
