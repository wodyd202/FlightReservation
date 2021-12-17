package com.ljy.flightreservation.services.reservation.application.external;

import com.ljy.flightreservation.services.reservation.domain.value.Booker;

public interface MemberRepository {
    Booker getMember(String id);
}
