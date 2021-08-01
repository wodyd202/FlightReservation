package com.ljy.flightreservation.user.query.presentation;

import com.ljy.flightreservation.user.query.application.QueryUserRepository;
import com.ljy.flightreservation.user.query.application.QueryUserService;
import com.ljy.flightreservation.user.query.domain.QueryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.x509.PrivateKeyUsageExtension;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
public class UserQueryAPI {
    @Autowired private QueryUserService userService;

    @GetMapping
    public ResponseEntity getUserInfo(Principal principal) {
        QueryUser queryUser = userService.findByUserId(principal.getName());
        return ResponseEntity.ok(queryUser);
    }
}
