package com.ljy.flightreservation.services.reservation.presentation;

import com.ljy.flightreservation.core.http.CommandException;
import com.ljy.flightreservation.services.reservation.application.ReservationService;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.application.model.ReservationModels;
import com.ljy.flightreservation.services.reservation.application.model.ReservationSearchDTO;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 예약 API
 */
@RequestMapping("api/reservation")
@RestController
@AllArgsConstructor
public class ReservationAPI {
    private final ReservationService reservationService;

    /**
     * @param reservationSeq
     * @param principal
     * # 예약 상세 정보 조회
     */
    @GetMapping("{reservationSeq}")
    public ResponseEntity<ReservationModel> getReservation(@PathVariable long reservationSeq, Principal principal){
        ReservationModel reservationModel = reservationService.getReservationModel(reservationSeq, principal.getName());
        return ResponseEntity.ok(reservationModel);
    }

    /**
     * @param flightSeq
     * # 해당 운항 정보의 이미 예약된 좌석들 가져오기
     */
    @GetMapping("flight/{flightSeq}")
    public ResponseEntity<ReservationModels> getReservationByFlightSeq(@PathVariable long flightSeq){
        ReservationModels reservationModels = reservationService.getReservationByFlightSeq(flightSeq);
        return ResponseEntity.ok(reservationModels);
    }

    /**
     * @param reservationSearchDTO
     * @param principal
     * # 예약 목록 가져오기
     */
    @GetMapping
    public ResponseEntity<ReservationModels> getReservations(ReservationSearchDTO reservationSearchDTO, Principal principal){
        ReservationModels reservationModels = reservationService.getReservationModels(reservationSearchDTO, principal.getName());
        return ResponseEntity.ok(reservationModels);
    }

    /**
     * @param reservate
     * @param errors
     * @param principal
     * # 예약
     */
    @PostMapping
    public ResponseEntity<ReservationModel> reserve(@Valid @RequestBody Reservate reservate, Errors errors, Principal principal){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
        ReservationModel reservationModel = reservationService.reserve(reservate, Booker.of(principal.getName()));
        return ResponseEntity.ok(reservationModel);
    }
}
