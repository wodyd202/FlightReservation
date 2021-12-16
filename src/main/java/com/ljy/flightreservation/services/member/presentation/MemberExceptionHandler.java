package com.ljy.flightreservation.services.member.presentation;

import com.ljy.flightreservation.services.member.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity error(MemberNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AlreadyExistMemberException.class)
    public ResponseEntity error(AlreadyExistMemberException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
