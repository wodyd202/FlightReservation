package com.ljy.flightreservation.services.flightInfo.domain.value;

import com.ljy.flightreservation.services.flightInfo.domain.exception.InvalidFightInfoException;
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
            throw new InvalidFightInfoException("invalid base price");
        }
        return new BasePrice(won);
    }

    public long get() {
        return price;
    }
}
