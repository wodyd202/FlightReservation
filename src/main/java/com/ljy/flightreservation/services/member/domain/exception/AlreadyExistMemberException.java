package com.ljy.flightreservation.services.member.domain.exception;

public class AlreadyExistMemberException extends RuntimeException {
    public AlreadyExistMemberException() {
        super("해당 아이디를 사용중인 회원이 이미 존재합니다.");
    }
}
