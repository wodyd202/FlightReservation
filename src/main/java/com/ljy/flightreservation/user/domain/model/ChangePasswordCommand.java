package com.ljy.flightreservation.user.domain.model;

import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.value.Passport;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordCommand {
    private String originPassword;
    private String changePassword;
}
