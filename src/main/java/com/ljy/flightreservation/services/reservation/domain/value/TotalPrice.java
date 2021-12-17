package com.ljy.flightreservation.services.reservation.domain.value;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "price")
public class TotalPrice {
    private final long price;

    protected TotalPrice(){price=0;}

    private TotalPrice(long price){
        this.price = price;
    }

    public static TotalPrice won(long won) {
        if(won < 0){
            throw new IllegalArgumentException("결제 금액은 0원 이상 입력해주세요.");
        }
        return new TotalPrice(won);
    }

    public long get() {
        return price;
    }
}
