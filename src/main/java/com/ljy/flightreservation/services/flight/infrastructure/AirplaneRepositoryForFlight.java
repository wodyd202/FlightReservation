package com.ljy.flightreservation.services.flight.infrastructure;

import com.ljy.flightreservation.services.airplane.domain.QAirplane;
import com.ljy.flightreservation.services.flight.application.external.AirplaneRepository;
import com.ljy.flightreservation.services.flight.domain.AirplaneInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ljy.flightreservation.services.airplane.domain.QAirplane.airplane;

@Repository
@Transactional(readOnly = true)
@AllArgsConstructor
public class AirplaneRepositoryForFlight implements AirplaneRepository {
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<AirplaneInfo> getAirplane(String airplaneCode) {
        return Optional.ofNullable(jpaQueryFactory.select(Projections.constructor(AirplaneInfo.class,
                            airplane.code().code))
                            .from(airplane)
                            .where(airplane.code().code.eq(airplaneCode))
                            .fetchFirst());
    }
}
