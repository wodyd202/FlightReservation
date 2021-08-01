package com.ljy.flightreservation.user.command.presentation;

import com.ljy.flightreservation.user.command.application.UserService;
import com.ljy.flightreservation.user.command.application.model.*;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
public class UserCommandAPI {
    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegisterUser command,
                                   Errors errors){
        verifyNotHasError(errors);
        UserModel userModel = userService.register(command);
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("password")
    public ResponseEntity changePassword(@Valid @RequestBody ChangePassword command,
                                         Errors errors,
                                         Principal principal){
        verifyNotHasError(errors);
        UserModel userModel = userService.changePassword(command, new UserId(principal.getName()));
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("passport")
    public ResponseEntity changePassport(@Valid @RequestBody ChangePassport command,
                                         Errors errors,
                                         Principal principal){
        verifyNotHasError(errors);
        UserModel userModel = userService.changePassport(command, new UserId(principal.getName()));
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("deposit")
    public ResponseEntity depositMoney(@Valid @RequestBody DepositMoney command,
                                       Errors errors,
                                       Principal principal){
        verifyNotHasError(errors);
        userService.deposit(command, new UserId(principal.getName()));
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public ResponseEntity withdrawal(@Valid @RequestBody Withdrawal command,
                                     Errors errors,
                                     Principal principal){
        verifyNotHasError(errors);
        userService.withdrawal(command, new UserId(principal.getName()));
        return ResponseEntity.ok("success");
    }

    private void verifyNotHasError(Errors errors){
        if(errors.hasErrors()){
            throw new CommandException(errors);
        }
    }
}
