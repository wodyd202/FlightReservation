package com.ljy.flightreservation.reservation;

import com.ljy.flightreservation.reservation.command.application.AirplaneRepository;
import com.ljy.flightreservation.reservation.command.application.FlightInfoRepository;
import com.ljy.flightreservation.reservation.command.application.ReservationRepository;
import com.ljy.flightreservation.reservation.command.application.BookerRepository;
import com.ljy.flightreservation.reservation.command.application.model.*;
import com.ljy.flightreservation.reservation.command.domain.agg.ReservationRequestValidator;
import com.ljy.flightreservation.reservation.command.domain.exception.InvalidReservationDateException;
import com.ljy.flightreservation.reservation.command.domain.exception.NecessaryPassportException;
import com.ljy.flightreservation.reservation.command.domain.exception.SitCodeNotFoundException;
import com.ljy.flightreservation.reservation.command.domain.value.*;
import com.ljy.flightreservation.reservation.command.domain.agg.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.ljy.flightreservation.reservation.ReservationFixture.aReservation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationTest {

    BookerRepository userRepository;
    FlightInfoRepository flightInfoRepository;
    AirplaneRepository airplaneRepository;
    ReservationRepository reservationRepository;

    ReservationRequestValidator reservationRequestValidator;

    @BeforeEach
    void setUp(){
        userRepository = mock(BookerRepository.class);
        flightInfoRepository = mock(FlightInfoRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        airplaneRepository = mock(AirplaneRepository.class);
        reservationRequestValidator = new ReservationRequestValidator(userRepository, flightInfoRepository, airplaneRepository, reservationRepository);
    }

    @Test
    void mapFrom(){
        CreateReservation reservationCommand = CreateReservation.builder()
                .flightInfoCode("flightInfoCode")
                .reservationSits(Arrays.asList("A3","B20"))
                .build();

        ReservationMapper mapper = new ReservationMapper();
        Reservation reservation = mapper.mapFrom(new ReservationCode(UUID.randomUUID().toString()), reservationCommand, "booker");
        assertNotNull(reservation);
    }

    @Test
    @DisplayName("예약 날짜가 오늘일 경우 시간의 30분 이전 까지만 예약할 수 있음")
    void invalidReservationDate2(){
        when(flightInfoRepository.findByFlightInfoCode("flightCode"))
                .thenReturn(Optional.of(FlightInfoModel.builder()
                        .airplaneCode("airplaneCode")
                        .needPassport("YES")
                        .departureHour(LocalDateTime.now().getHour())
                        .departureMinute(LocalDateTime.now().getMinute())
                        .build()));

        when(airplaneRepository.findByAirplaneCode("airplaneCode"))
                .thenReturn(Optional.of(AirplaneModel.builder()
                        .sits(Arrays.asList(SitModel.builder().code("A1").build()))
                        .build()));

        when(userRepository.findByBooker("booker"))
                .thenReturn(Optional.of(UserModel.builder()
                        .passport("passport")
                        .build()));

        Reservation reservation = aReservation()
                .sitCodes(new SitCodes(Arrays.asList("A1")))
                .flightInfoCode(new FlightInfoCode("flightCode"))
                .booker(new Booker("booker"))
                .reservationDate(LocalDate.now())
                .build();

        assertThrows(InvalidReservationDateException.class, ()->{
            reservation.request(new ReservationCode(UUID.randomUUID().toString()), reservationRequestValidator);
        });
    }

    @Test
    @DisplayName("예약 날짜가 과거의 날짜면 안됨")
    void invalidReservationDate1(){
        when(flightInfoRepository.findByFlightInfoCode("flightCode"))
                .thenReturn(Optional.of(FlightInfoModel.builder()
                        .airplaneCode("airplaneCode")
                        .needPassport("YES")
                        .build()));

        when(airplaneRepository.findByAirplaneCode("airplaneCode"))
                .thenReturn(Optional.of(AirplaneModel.builder()
                        .sits(Arrays.asList(SitModel.builder().code("A1").build()))
                        .build()));

        when(userRepository.findByBooker("booker"))
                .thenReturn(Optional.of(UserModel.builder()
                        .passport("passport")
                        .build()));

        Reservation reservation = aReservation()
                .sitCodes(new SitCodes(Arrays.asList("A1")))
                .flightInfoCode(new FlightInfoCode("flightCode"))
                .booker(new Booker("booker"))
                .reservationDate(LocalDate.of(2021,8,03))
                .build();

        assertThrows(InvalidReservationDateException.class, ()->{
            reservation.request(new ReservationCode(UUID.randomUUID().toString()), reservationRequestValidator);
        });
    }

    @Test
    @DisplayName("여권이 필요한 항공편을 예약할 경우 사용자가 여권을 갖고있어야함")
    void necessraryPassport(){
        when(flightInfoRepository.findByFlightInfoCode("flightCode"))
                .thenReturn(Optional.of(FlightInfoModel.builder()
                        .airplaneCode("airplaneCode")
                        .needPassport("YES")
                        .build()));

        when(airplaneRepository.findByAirplaneCode("airplaneCode"))
                .thenReturn(Optional.of(AirplaneModel.builder()
                        .sits(Arrays.asList(SitModel.builder().code("A1").build()))
                        .build()));

        when(userRepository.findByBooker("booker"))
                .thenReturn(Optional.of(UserModel.builder()
                        .passport(null)
                        .build()));

        Reservation reservation = aReservation()
                .sitCodes(new SitCodes(Arrays.asList("A1")))
                .flightInfoCode(new FlightInfoCode("flightCode"))
                .booker(new Booker("booker"))
                .reservationDate(LocalDate.now().plusWeeks(1))
                .build();

        assertThrows(NecessaryPassportException.class, ()->{
            reservation.request(new ReservationCode(UUID.randomUUID().toString()), reservationRequestValidator);
        });
    }

    @Test
    @DisplayName("해당 좌석이 존재하지 않으면 안됨")
    void invalidSitsCode(){
        when(flightInfoRepository.findByFlightInfoCode("flightCode"))
                .thenReturn(Optional.of(FlightInfoModel.builder()
                        .airplaneCode("airplaneCode")
                        .needPassport("NO")
                        .build()));

        when(airplaneRepository.findByAirplaneCode("airplaneCode"))
                .thenReturn(Optional.of(AirplaneModel.builder()
                        .sits(Arrays.asList(SitModel.builder().code("A1").build()))
                        .build()));

        when(userRepository.findByBooker("booker"))
                .thenReturn(Optional.of(UserModel.builder().build()));

        Reservation reservation = aReservation()
                .sitCodes(new SitCodes(Arrays.asList("A0")))
                .flightInfoCode(new FlightInfoCode("flightCode"))
                .reservationDate(LocalDate.now().plusWeeks(1))
                .build();

        assertThrows(SitCodeNotFoundException.class, ()->{
            reservation.request(new ReservationCode(UUID.randomUUID().toString()), reservationRequestValidator);
        });
    }

    @Test
    @DisplayName("예약 완료")
    void reservationRequest(){
        when(flightInfoRepository.findByFlightInfoCode("flightCode"))
                .thenReturn(Optional.of(FlightInfoModel.builder()
                        .airplaneCode("airplaneCode")
                        .needPassport("NO")
                        .build()));

        when(airplaneRepository.findByAirplaneCode("airplaneCode"))
                .thenReturn(Optional.of(AirplaneModel.builder()
                        .sits(Arrays.asList(SitModel.builder().code("A1").build()))
                        .build()));

        when(userRepository.findByBooker("booker"))
                .thenReturn(Optional.of(UserModel.builder().build()));

        Reservation reservation = aReservation()
                .sitCodes(new SitCodes(Arrays.asList("A1")))
                .flightInfoCode(new FlightInfoCode("flightCode"))
                .booker(new Booker("booker"))
                .reservationDate(LocalDate.now().plusWeeks(1))
                .build();

        reservation.request(new ReservationCode(UUID.randomUUID().toString()), reservationRequestValidator);

        assertEquals(reservation.getState(), ReservationState.SUCCESS);
    }
}
