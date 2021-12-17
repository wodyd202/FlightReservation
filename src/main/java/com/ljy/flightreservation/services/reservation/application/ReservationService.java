package com.ljy.flightreservation.services.reservation.application;

import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.application.model.ReservationModels;
import com.ljy.flightreservation.services.reservation.application.model.ReservationSearchDTO;
import com.ljy.flightreservation.services.reservation.domain.RegisterReservationValidator;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.ReservationRepository;
import com.ljy.flightreservation.services.reservation.domain.exception.ReservationNotFoundException;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 예약 서비스
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    // util
    private final RegisterReservationValidator registerReservationValidator;
    private final ReservationCreator reservationCreator;

    /**
     * @param reservate
     * @param booker
     * # 예약
     */
    public ReservationModel reserve(Reservate reservate, Booker booker) {
        Reservation reservation = reservationCreator.create(reservate, booker);

        reservation.reserve(registerReservationValidator);
        reservationRepository.save(reservation);

        ReservationModel reservationModel = reservation.toModel();
        log.info("save reservation into database : {}", reservationModel);
        return reservationModel;
    }

    /**
     * @param reservationSearchDTO
     * @param booker
     * # 예약 목록 가져오기
     */
    public ReservationModels getReservationModels(ReservationSearchDTO reservationSearchDTO, String booker) {
        return ReservationModels.builder()
                .reservations(reservationRepository.findAll(reservationSearchDTO, booker))
                .totalElement(reservationRepository.countAll(reservationSearchDTO, booker))
                .build();
    }

    /**
     * @param reservationSeq
     * @param booker
     * # 예약 상세 정보 조회
     */
    public ReservationModel getReservationModel(long reservationSeq, String booker) {
        return reservationRepository.findByIdAndBooker(reservationSeq, booker).orElseThrow(ReservationNotFoundException::new);
    }

    /**
     * @param flightSeq
     * # 해당 운항 정보의 이미 예약된 좌석들 가져오기
     */
    public ReservationModels getReservationByFlightSeq(long flightSeq) {
        return ReservationModels.builder()
                .totalElement(reservationRepository.countByFlightSeq(flightSeq))
                .reservations(reservationRepository.findByFlightSeq(flightSeq))
                .build();
    }
}
