package com.ljy.flightreservation.services.reservation;

import com.ljy.flightreservation.IntegrationTest;
import com.ljy.flightreservation.services.reservation.domain.RegisterReservationValidator;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.ReservationRepository;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class ReservationIntegrationTest extends IntegrationTest {
    @Autowired ReservationRepository reservationRepository;

    protected ReservationModel newReservation(Reservation.ReservationBuilder reservationBuilder){
        Reservation reservation = reservationBuilder.build();
        reservation.reserve(mock(RegisterReservationValidator.class));
        reservationRepository.save(reservation);
        return reservation.toModel();
    }
}
