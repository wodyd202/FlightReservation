package com.ljy.flightreservation.services.member.application.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeMember {
    @Valid
    private ChangePassword changePassword;

    @Pattern(message = "여권 번호 형식이 유효하지 않습니다.", regexp = "([a-zA-Z]{1}|[a-zA-Z]{2})\\d{8}")
    private String passport;

    @Pattern(message = "이메일 형식이 올바르지 않습니다.", regexp = "[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$")
    private String email;
}
