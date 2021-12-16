package com.ljy.flightreservation.services.common;

import com.ljy.flightreservation.core.http.CommandException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity error(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CommandException.class)
    public ResponseEntity error(CommandException e){
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
}
