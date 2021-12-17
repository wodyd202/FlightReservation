package com.ljy.flightreservation.services.reservation.application;

import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.RegisterAirplaneValidator;
import com.ljy.flightreservation.services.airplane.domain.value.*;
import com.ljy.flightreservation.services.flight.domain.AirplaneInfo;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.ljy.flightreservation.services.flight.domain.RegisterFlightValidator;
import com.ljy.flightreservation.services.flight.domain.value.BasePrice;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.RegisterMemberValidator;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Money;
import com.ljy.flightreservation.services.reservation.ReservationIntegrationTest;
import com.ljy.flightreservation.services.reservation.application.model.Reservate;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.Booker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.ljy.flightreservation.services.airplane.AirplaneFixture.aAirplane;
import static com.ljy.flightreservation.services.flight.FlightFixtrue.aFlight;
import static com.ljy.flightreservation.services.member.MemberFixture.aMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * 예약 서비스 테스트
 */
public class ReservationService_Test extends ReservationIntegrationTest {
    @Autowired ReservationService reservationService;

    // external
    @Autowired FlightRepository flightRepository;
    @Autowired AirplaneRepository airplaneRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원의 잔액이 부족할 경우 에러")
    void lackMoeny(){
        newMember(aMember().id(MemberId.of("lackmoney")), Money.won(0));
        newAirplane(aAirplane()
                .code(AirplaneCode.of("airplaneCode_4"))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C3"))
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(Arrays.asList("S1","B1"))
                                .sitSurcharge(30_000)
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(Arrays.asList("C1", "C2"))
                                .sitSurcharge(50_000)
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(7)
                                .lastRowSitCode("Z")
                                .build())
                        .build()));
        long flightInfoSeq = newFlight(aFlight().airplaneInfo(AirplaneInfo.builder()
                        .code("airplaneCode_4")
                        .build())
                .basePrice(BasePrice.won(100_000)));
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("C1")
                .build();

        // when
        assertThrows(IllegalArgumentException.class, ()->{
            reservationService.reserve(reservate, Booker.of("lackmoney"));
        });
    }

    @Test
    @DisplayName("존재하지 않는 좌석 예약 시 에러")
    void notExistSitCode(){
        // given
        newMember(aMember().id(MemberId.of("notexistsit")), Money.won(1_000_000));
        newAirplane(aAirplane()
                .code(AirplaneCode.of("airplaneCode_3"))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C3"))
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(Arrays.asList("S1","B1"))
                                .sitSurcharge(30_000)
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(Arrays.asList("C1", "C2"))
                                .sitSurcharge(50_000)
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(7)
                                .lastRowSitCode("Z")
                                .build())
                        .build()));
        long flightInfoSeq = newFlight(aFlight().airplaneInfo(AirplaneInfo.builder()
                        .code("airplaneCode_3")
                        .build())
                .basePrice(BasePrice.won(100_000)));
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("C3")
                .build();

        // when
        assertThrows(IllegalArgumentException.class, ()->{
            reservationService.reserve(reservate, Booker.of("notexistsit"));
        });
    }

    @Test
    @DisplayName("스페셜 좌석 예약")
    void specialSitReserve(){
        // given
        newMember(aMember().id(MemberId.of("specialbook")), Money.won(1_000_000));
        newAirplane(aAirplane()
                .code(AirplaneCode.of("airplaneCode_2"))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C3"))
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(Arrays.asList("S1","B1"))
                                .sitSurcharge(30_000)
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(Arrays.asList("C1", "C2"))
                                .sitSurcharge(50_000)
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(7)
                                .lastRowSitCode("Z")
                                .build())
                        .build()));
        long flightInfoSeq = newFlight(aFlight().airplaneInfo(AirplaneInfo.builder()
                        .code("airplaneCode_2")
                        .build())
                .basePrice(BasePrice.won(100_000)));
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("C1")
                .build();

        // when
        ReservationModel reservationModel = reservationService.reserve(reservate, Booker.of("specialbook"));

        // then
        assertEquals(reservationModel.getPrice(), 150_000);
    }

    @Test
    @DisplayName("비즈니스석 예약")
    void businessSitReserve(){
        // given
        newMember(aMember().id(MemberId.of("businessreserve")), Money.won(1_000_000));
        newAirplane(aAirplane()
                .code(AirplaneCode.of("airplaneCode_1"))
                .sitInfo(SitInfo.builder()
                        .ignoreSitInfo(IgnoreSitInfo.builder()
                                .sitList(Arrays.asList("C3"))
                                .build())
                        .businessSitInfo(BusinessSitInfo.builder()
                                .sitList(Arrays.asList("S1","B1"))
                                .sitSurcharge(30_000)
                                .build())
                        .specialSitInfo(SpecialSitInfo.builder()
                                .sitList(Arrays.asList("C1", "C2"))
                                .sitSurcharge(50_000)
                                .build())
                        .sitCodeInfo(SitCodeInfo.builder()
                                .lastColSitNumber(7)
                                .lastRowSitCode("Z")
                                .build())
                        .build()));
        long flightInfoSeq = newFlight(aFlight().airplaneInfo(AirplaneInfo.builder()
                .code("airplaneCode_1")
                .build())
                .basePrice(BasePrice.won(100_000)));
        Reservate reservate = Reservate.builder()
                .flightInfoSeq(flightInfoSeq)
                .sitCode("S1")
                .build();

        // when
        ReservationModel reservationModel = reservationService.reserve(reservate, Booker.of("businessreserve"));

        // then
        assertEquals(reservationModel.getPrice(), 130_000);
    }

    void newMember(Member.MemberBuilder memberBuilder, Money money){
        Member member = memberBuilder.build();
        member.deposit(money);
        member.register(mock(RegisterMemberValidator.class));
        MemberModel memberModel = member.toModel();
        if(!memberRepository.findById(MemberId.of(memberModel.getId())).isPresent()){
            memberRepository.save(member);
        }
    }

    private void newAirplane(Airplane.AirplaneBuilder airplaneBuilder) {
        Airplane airplane = airplaneBuilder.build();
        airplane.register(mock(RegisterAirplaneValidator.class));
        airplaneRepository.save(airplane);
    }

    private long newFlight(Flight.FlightBuilder flightBuilder) {
        Flight flight = flightBuilder.build();
        flight.register(mock(RegisterFlightValidator.class));
        flightRepository.save(flight);
        return flight.toModel().getSeq();
    }
}
