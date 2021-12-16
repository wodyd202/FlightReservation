package com.ljy.flightreservation.services.member.domain.exception;

public class AlreadyExistMemberException extends IllegalStateException {
    public AlreadyExistMemberException(String msg) {
        super(msg);
    }
}
