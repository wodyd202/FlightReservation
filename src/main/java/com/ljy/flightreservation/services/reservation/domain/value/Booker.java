package com.ljy.flightreservation.services.reservation.domain.value;

import com.ljy.flightreservation.services.member.domain.value.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booker {
    private String id;

    @Transient
    private long money;

    public static Booker of(String id){
        return new Booker(id);
    }

    private Booker(String id) {
        this.id = id;
    }

    @Builder
    public Booker(String id, Money money) {
        this.id = id;
        this.money = money.get();
    }

    public boolean ableReservation(TotalPrice totalPrice) {
        return totalPrice.get() <= money;
    }
}

