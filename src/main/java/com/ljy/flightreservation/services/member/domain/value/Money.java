package com.ljy.flightreservation.services.member.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "won")
public class Money {
    private final long won;

    public Money plus(long won) {
        return Money.won(this.won + won);
    }

    public Money minus(long won) {
        verifySufficientMoney(won);
        return Money.won(this.won - won);
    }

    private void verifySufficientMoney(long won) {
        if(this.won < won){
            throw new IllegalArgumentException("금액이 부족합니다.");
        }
    }

    Money(long won) {
        this.won = won;
    }

    public static Money won(long won){
        return new Money(won);
    }

    public long get() {
        return won;
    }

}
