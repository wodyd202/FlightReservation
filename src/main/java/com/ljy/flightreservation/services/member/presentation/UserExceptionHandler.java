package com.ljy.flightreservation.services.member.presentation;

import com.ljy.flightreservation.core.http.CommandException;
import com.ljy.flightreservation.services.member.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            AlreadyExistMemberException.class,
            UserNotFoundException.class,
            IllegalStateException.class
    })
    public ResponseEntity error(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CommandException.class)
    public ResponseEntity error(CommandException e){
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
}
