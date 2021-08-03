package com.ljy.flightreservation.user.command.presentation;

import com.ljy.flightreservation.core.http.CommandException;
import com.ljy.flightreservation.user.command.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            AlreadyDeletedUserException.class,
            AlreadyExistUserException.class,
            UserNotFoundException.class
    })
    public ResponseEntity error(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({
            InvalidEmailException.class,
            InvalidPasswordException.class,
            InvalidPassportException.class,
            InvalidUserIdException.class,
            MoneyException.class
    })
    public ResponseEntity error(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CommandException.class)
    public ResponseEntity error(CommandException e){
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
}
