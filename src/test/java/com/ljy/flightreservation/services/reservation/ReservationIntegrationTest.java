package com.ljy.flightreservation.services.reservation;

import com.ljy.flightreservation.IntegrationTest;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.RegisterAirplaneValidator;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.ljy.flightreservation.services.flight.domain.RegisterFlightValidator;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.RegisterMemberValidator;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Money;
import com.ljy.flightreservation.services.reservation.domain.RegisterReservationValidator;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.ReservationRepository;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class ReservationIntegrationTest extends IntegrationTest {
    @Autowired ReservationRepository reservationRepository;

    // external
    @Autowired FlightRepository flightRepository;
    @Autowired AirplaneRepository airplaneRepository;
    @Autowired MemberRepository memberRepository;

    protected ReservationModel newReservation(Reservation.ReservationBuilder reservationBuilder){
        Reservation reservation = reservationBuilder.build();
        reservation.reserve(mock(RegisterReservationValidator.class));
        reservationRepository.save(reservation);
        return reservation.toModel();
    }

    protected void newMember(Member.MemberBuilder memberBuilder, Money money){
        Member member = memberBuilder.build();
        member.deposit(money);
        member.register(mock(RegisterMemberValidator.class));
        MemberModel memberModel = member.toModel();
        if(!memberRepository.findById(MemberId.of(memberModel.getId())).isPresent()){
            memberRepository.save(member);
        }
    }

    protected void newAirplane(Airplane.AirplaneBuilder airplaneBuilder) {
        Airplane airplane = airplaneBuilder.build();
        airplane.register(mock(RegisterAirplaneValidator.class));
        if(!airplaneRepository.findById(airplane.toModel().getCode()).isPresent()){
            airplaneRepository.save(airplane);
        }
    }

    protected long newFlight(Flight.FlightBuilder flightBuilder) {
        Flight flight = flightBuilder.build();
        flight.register(mock(RegisterFlightValidator.class));
        flightRepository.save(flight);
        return flight.toModel().getSeq();
    }
}
