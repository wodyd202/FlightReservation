package com.ljy.flightreservation.services.airplane.presentation;

import com.ljy.flightreservation.services.airplane.domain.exception.AirplaneNotFoundException;
import com.ljy.flightreservation.services.airplane.domain.exception.AlreadyExistAirplaneException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AirplaneExceptionHandler {

    @ExceptionHandler(AirplaneNotFoundException.class)
    public ResponseEntity error(AirplaneNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AlreadyExistAirplaneException.class)
    public ResponseEntity error(AlreadyExistAirplaneException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
