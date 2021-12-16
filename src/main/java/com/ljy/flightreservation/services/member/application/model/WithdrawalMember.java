package com.ljy.flightreservation.services.member.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalMember {

    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    @Size(min = 5, max = 15, message = "기존 비밀번호는 5자 이상 15자 이하로 입력해주세요.")
    private String originPassword;
}
