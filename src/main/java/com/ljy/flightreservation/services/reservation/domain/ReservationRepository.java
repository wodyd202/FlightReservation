package com.ljy.flightreservation.services.reservation.domain;

import com.ljy.flightreservation.services.reservation.application.model.ReservationSearchDTO;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    void save(Reservation reservation);
    Optional<Reservation> findByFlightSeqAndSitCode(long seq, SitInfo sitInfo);

    Optional<ReservationModel> findByIdAndBooker(long seq, String booker);
    List<ReservationModel> findAll(ReservationSearchDTO reservationSearchDTO, String booker);
    long countAll(ReservationSearchDTO reservationSearchDTO, String booker);
}
