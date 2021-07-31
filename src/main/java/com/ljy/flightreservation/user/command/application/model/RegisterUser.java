package com.ljy.flightreservation.user.command.application.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterUser {
    @NotBlank(message = "id must not be empty")
    @Pattern(regexp = "^[0-9a-z]*$", message = "invalid id")
    private String id;

    @NotBlank(message = "password must not be empty")
    @Size(min = 5, max = 15, message = "password size must be 5 or more and 15 or less")
    private String password;

    @Email(message = "invalid email")
    @NotBlank(message = "email must not be empty")
    private String email;

    @Pattern(regexp = "([a-zA-Z]{1}|[a-zA-Z]{2})\\d{8}", message = "invalid passport")
    private String passport;
}
