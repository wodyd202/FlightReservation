package com.ljy.flightreservation.services.reservation.infrastructure;

import com.ljy.flightreservation.services.flight.domain.QFlight;
import com.ljy.flightreservation.services.reservation.application.external.FlightRepository;
import com.ljy.flightreservation.services.reservation.domain.value.FlightInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ljy.flightreservation.services.flight.domain.QFlight.flight;

@Repository
@Transactional(readOnly = true)
@AllArgsConstructor
public class FlightRepositoryForReservation implements FlightRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<FlightInfo> getFlightInfo(long seq) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.constructor(FlightInfo.class,
                                flight.seq,
                                flight.basePrice,
                                flight.airplaneInfo().code,
                                flight.flightDetail().departureDate,
                                flight.flightDetail().departureTime,
                                flight.flightDetail().arrivalDate,
                                flight.flightDetail().estimatedArrivalTime,
                                flight.flightDetail().arrivalArea
                            ))
                        .from(flight)
                        .where(flight.seq.eq(seq))
                        .fetchFirst()
        );
    }
}
