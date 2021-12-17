package com.ljy.flightreservation.services.reservation.presentation;

import com.ljy.flightreservation.services.member.domain.exception.MemberNotFoundException;
import com.ljy.flightreservation.services.reservation.domain.exception.ReservationNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity error(ReservationNotFoundException e){
        return ResponseEntity.notFound().build();
    }
}
