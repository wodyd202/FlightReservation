package com.ljy.flightreservation.user.command.domain.value;

import com.ljy.flightreservation.user.command.domain.exception.MoneyException;
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
            throw new MoneyException("insufficient balance");
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
