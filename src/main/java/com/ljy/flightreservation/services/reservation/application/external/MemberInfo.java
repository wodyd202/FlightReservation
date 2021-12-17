package com.ljy.flightreservation.services.reservation.application.external;

import com.ljy.flightreservation.services.member.domain.value.Money;
import com.ljy.flightreservation.services.member.domain.value.Passport;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {
    private String id;
    private long money;
    private String passport;

    @Builder
    public MemberInfo(String id, Money money, Passport passport) {
        this.id = id;
        this.money = money.get();
        this.passport = passport == null ? null : passport.get();
    }

    public boolean ableReservation(TotalPrice totalPrice) {
        return totalPrice.get() <= money;
    }
}
