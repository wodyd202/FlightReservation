package com.ljy.flightreservation.services.flightInfo.application.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationFlightInfo {

    @NotBlank(message = "arrival area must not be empty")
    private String arrivalArea;

    @NotBlank(message = "departure area must not be empty")
    private String departureArea;

    @NotNull(message = "departure hour must not be empty")
    @Min(value = 0, message = "departure hour must be 0 or more")
    @Max(value = 23, message = "departure hour must be 23 or less")
    private Integer departureHour;

    @NotNull(message = "departure minute must not be empty")
    @Min(value = 0, message = "departure minute must be 0 or more")
    @Max(value = 23, message = "departure minute must be 59 or less")
    private Integer departureMinute;

    @NotNull(message = "arrival hour must not be empty")
    @Min(value = 0, message = "arrival hour must be 0 or more")
    @Max(value = 23, message = "arrival hour must be 23 or less")
    private Integer arrivalHour;

    @NotNull(message = "arrival minute must not be empty")
    @Min(value = 0, message = "arrival minute must be 0 or more")
    @Max(value = 23, message = "arrival minute must be 59 or less")
    private Integer arrivalMinute;

    @NotNull(message = "price must not be empty")
    @Min(value = 0, message = "private minute must be 100 or more")
    private Integer price;

    @NotBlank(message = "airplane code must not be empty")
    private String airplaneCode;

    @NotNull(message = "needPassport must not be empty")
    private Boolean needPassport;
}
