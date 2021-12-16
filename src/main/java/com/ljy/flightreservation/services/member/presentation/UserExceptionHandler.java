package com.ljy.flightreservation.services.member.presentation;

import com.ljy.flightreservation.services.member.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity error(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            AlreadyExistMemberException.class,
            UserNotFoundException.class
    })
    public ResponseEntity error(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
