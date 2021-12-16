package com.ljy.flightreservation.services.common;

import com.ljy.flightreservation.core.http.CommandException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(CommandException.class)
    public ResponseEntity error(CommandException e){
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
}
