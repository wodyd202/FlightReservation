package com.ljy.flightreservation.services.reservation.application;

import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.domain.RegisterReservationValidator;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.ReservationRepository;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    // util
    private final RegisterReservationValidator registerReservationValidator;
    private final ReservationCreator reservationCreator;

    public ReservationModel reserve(Reservate reservate, Booker booker) {
        Reservation reservation = reservationCreator.create(reservate, booker);

        reservation.reserve(registerReservationValidator);
        reservationRepository.save(reservation);

        ReservationModel reservationModel = reservation.toModel();
        log.info("save reservation into database : {}", reservationModel);
        return reservationModel;
    }
}
