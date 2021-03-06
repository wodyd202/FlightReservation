package com.ljy.flightreservation.services.flight.domain.value;

import com.ljy.flightreservation.services.flight.domain.exception.InvalidFightInfoException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "price")
public class BasePrice {
    private final long price;

    protected BasePrice(){price=0;}

    private BasePrice(long price){
        this.price = price;
    }

    public static BasePrice won(long won) {
        if(won < 100){
            throw new InvalidFightInfoException("기본 가격은 100원 이상 입력해주세요.");
        }
        return new BasePrice(won);
    }

    public long get() {
        return price;
    }
}
