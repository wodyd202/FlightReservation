package com.ljy.flightreservation.services.reservation.domain;

import com.ljy.flightreservation.services.reservation.application.PassportValidator;
import com.ljy.flightreservation.services.reservation.application.external.MemberInfo;
import com.ljy.flightreservation.services.reservation.application.external.MemberRepository;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;
import com.ljy.flightreservation.services.reservation.domain.value.TotalPrice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterReservationValidator {
    private final ReservationRepository reservationRepository;

    // external
    private final MemberRepository memberRepository;
    private final PassportValidator passportValidator;

    public void validation(FlightInfo flightInfo, SitInfo sitInfo, Booker booker, TotalPrice totalPrice) {
        if(reservationRepository.findByFlightSeqAndSitCode(flightInfo.getSeq(), sitInfo).isPresent()){
            throw new IllegalArgumentException("이미 다른 회원이 예약하였습니다.");
        }

        MemberInfo member = memberRepository.getMember(booker.getId());
        if(!flightInfo.ableReservate(member, passportValidator)){
            throw new IllegalStateException("여권이 등록되어있지 않습니다. 여권을 등록해주세요.");
        }

        if(!member.ableReservation(totalPrice)){
            throw new IllegalArgumentException("금액이 부족합니다.");
        }
    }
}
