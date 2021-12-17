package com.ljy.flightreservation.services.reservation.application.external;

import com.ljy.flightreservation.services.member.domain.value.Money;
import com.ljy.flightreservation.services.member.domain.value.Passport;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
    private String id;
    private long money;
    private String passport;

    @Builder
    public Member(String id, Money money, Passport passport) {
        this.money = money.get();
        this.passport = passport.get();
    }

    public boolean ableReservation(TotalPrice totalPrice) {
        return totalPrice.get() <= money;
    }
}
