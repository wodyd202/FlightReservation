package com.ljy.flightreservation.services.reservation.infrastructure;

import com.ljy.flightreservation.services.airplane.domain.QAirplane;
import com.ljy.flightreservation.services.reservation.application.external.AirplaneRepository;
import com.ljy.flightreservation.services.reservation.application.external.AirplaneSitInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.flightreservation.services.airplane.domain.QAirplane.airplane;

@Repository
@Transactional(readOnly = true)
@AllArgsConstructor
public class AirplaneRepositoryForReservation implements AirplaneRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AirplaneSitInfo getAirplaneSitInfo(String airplaneCode) {
        return jpaQueryFactory.select(Projections.constructor(AirplaneSitInfo.class,
                        airplane.sitInfo().specialSitInfo(),
                        airplane.sitInfo().businessSitInfo(),
                        airplane.sitInfo().ignoreSitInfo()
                        ))
                .from(airplane)
                .where(airplane.code().code.eq(airplaneCode))
                .fetchFirst();
    }
}
