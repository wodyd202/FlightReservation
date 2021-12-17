package com.ljy.flightreservation.services.reservation.presentation;

import com.ljy.flightreservation.core.http.CommandException;
import com.ljy.flightreservation.services.reservation.application.ReservationService;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
