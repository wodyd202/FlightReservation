package com.ljy.flightreservation.services.member.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "won")
public class Money {
    private final long won;

    public Money plus(Money won) {
        return Money.won(this.won + won.get());
    }

    public Money minus(Money won) {
        verifySufficientMoney(won);
        return Money.won(this.won - won.get());
    }

    private void verifySufficientMoney(Money won) {
        if(this.won < won.get()){
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
