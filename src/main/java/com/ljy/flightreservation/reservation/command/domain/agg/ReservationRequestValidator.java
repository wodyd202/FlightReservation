package com.ljy.flightreservation.reservation.command.domain.agg;

import com.ljy.flightreservation.reservation.command.application.AirplaneRepository;
import com.ljy.flightreservation.reservation.command.application.FlightInfoRepository;
import com.ljy.flightreservation.reservation.command.application.ReservationRepository;
import com.ljy.flightreservation.reservation.command.application.BookerRepository;
import com.ljy.flightreservation.reservation.command.application.model.AirplaneModel;
import com.ljy.flightreservation.reservation.command.application.model.FlightInfoModel;
import com.ljy.flightreservation.reservation.command.application.model.SitModel;
import com.ljy.flightreservation.reservation.command.application.model.UserModel;
import com.ljy.flightreservation.reservation.command.domain.exception.*;
import com.ljy.flightreservation.reservation.command.domain.value.Booker;
import com.ljy.flightreservation.reservation.command.domain.value.FlightInfoCode;
import com.ljy.flightreservation.reservation.command.domain.value.SitCodes;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReservationRequestValidator {
    private final BookerRepository userRepository;
    private final FlightInfoRepository flightInfoRepository;
    private final ReservationRepository reservationRepository;
    private final AirplaneRepository airplaneRepository;

    public ReservationRequestValidator(BookerRepository userRepository,
                                       FlightInfoRepository flightInfoRepository,
                                       AirplaneRepository airplaneRepository,
                                       ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.flightInfoRepository = flightInfoRepository;
        this.reservationRepository = reservationRepository;
        this.airplaneRepository = airplaneRepository;
    }

    public long validationAfterReturnTotalPrice(Booker booker, FlightInfoCode flightInfoCode, SitCodes sitCodes, LocalDate departureDate) {
        verifyNotPastDateDepartureDate(departureDate);
        FlightInfoModel flightInfo = getFlightInfo(flightInfoCode);
        verifyIsThereMoreThan30MinutesLeft(flightInfo, departureDate);

        AirplaneModel airplane = getAirplane(flightInfo);
        long price = calcaulteTotalPrice(flightInfo, airplane.getSits());

        UserModel user = getBooker(booker);
        verifyNecessaryPassport(flightInfo.getNeedPassport(), user.getPassport());
        verifyIsBalanceSufficient(user, price);

        verifyExistSitCodes(sitCodes, airplane.getSits());

        return price;
    }

    private void verifyNotPastDateDepartureDate(LocalDate departureDate) {
        if(departureDate.isBefore(LocalDate.now())){
            throw new InvalidReservationDateException("departure date must not be past date");
        }
    }

    private void verifyIsThereMoreThan30MinutesLeft(FlightInfoModel flightInfo, LocalDate departureDate) {
        if(!departureDate.equals(LocalDate.now())){
            return;
        }
        if(flightInfo.getDepartureHour() < LocalDateTime.now().getHour()){
            throw new InvalidReservationDateException("operation has ended");
        }
        if(flightInfo.getDepartureHour() == LocalDateTime.now().getHour()){
            if(Math.abs(flightInfo.getDepartureMinute() - LocalDateTime.now().getMinute()) < 30){
                throw new InvalidReservationDateException("It is not possible to reserve service information with less than 30 minutes left of departure time");
            }
        }
    }

    private UserModel getBooker(Booker booker) {
        return userRepository.findByBooker(booker.get())
                .orElseThrow(() -> new BookerNotFoundException("not found booker"));
    }

    private FlightInfoModel getFlightInfo(FlightInfoCode flightInfoCode) {
        return flightInfoRepository.findByFlightInfoCode(flightInfoCode.get())
                .orElseThrow(() -> new FlightInfoNotFoundException("not found flight info"));
    }

    private AirplaneModel getAirplane(FlightInfoModel flightInfo) {
        return airplaneRepository.findByAirplaneCode(flightInfo.getAirplaneCode())
                .orElseThrow(() -> new AirplaneNotFoundExcception("not found airplane"));
    }

    private void verifyNecessaryPassport(String needPassport, String passport) {
        if(needPassport.equals("YES") && Objects.isNull(passport)){
            throw new NecessaryPassportException("necessary passport");
        }
    }

    private void verifyIsBalanceSufficient(UserModel user, long price) {
        if(price > user.getMoney()){
            throw new UnBalanceSufficientException("unbalance sufficient");
        }
    }

    private void verifyExistSitCodes(SitCodes sitCodes, List<SitModel> sits) {
        List<String> originSitCodes = sits.stream().map(sit -> sit.getCode()).collect(Collectors.toList());
        sitCodes.get().forEach(sit -> {
            if (!originSitCodes.contains(sit)) {
                throw new SitCodeNotFoundException("sit code not found");
            }
        });
    }

    private long calcaulteTotalPrice(FlightInfoModel flightInfo, List<SitModel> sits) {
        long price = flightInfo.getPrice();
        for(SitModel sit : sits){
            price += sit.getExtraPrice();
        }
        return price;
    }
}
