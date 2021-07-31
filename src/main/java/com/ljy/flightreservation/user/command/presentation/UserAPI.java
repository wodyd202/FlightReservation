package com.ljy.flightreservation.user.command.presentation;

import com.ljy.flightreservation.user.command.application.UserService;
import com.ljy.flightreservation.user.command.application.model.RegisterUser;
import com.ljy.flightreservation.user.command.application.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/user")
public class UserAPI {
    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterUser command, Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
        UserModel register = userService.register(command);
        return ResponseEntity.ok(register);
    }
}
