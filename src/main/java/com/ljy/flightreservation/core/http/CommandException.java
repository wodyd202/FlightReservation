package com.ljy.flightreservation.core.http;

import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CommandException extends IllegalArgumentException {
    private final Errors errors;

    public CommandException(Errors errors) {
        this.errors = errors;
    }

    public List<String> getErrorMessages(){
        return errors.getAllErrors().stream()
                                    .map(c->c.getDefaultMessage())
                                    .collect(toList());
    }
}
