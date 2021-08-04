package com.ljy.flightreservation.reservation.command.infrastructure;

import com.ljy.flightreservation.flightInfo.domain.agg.QFlightInfo;
import com.ljy.flightreservation.flightInfo.domain.value.FlightInfoCode;
import com.ljy.flightreservation.flightInfo.domain.value.FlightInfoState;
import com.ljy.flightreservation.reservation.command.application.FlightInfoRepository;
import com.ljy.flightreservation.reservation.command.application.model.FlightInfoModel;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ljy.flightreservation.flightInfo.domain.agg.QFlightInfo.flightInfo;

@Repository
@Transactional(readOnly = true)
public class QuerydslReservationFlightInfoRepository implements FlightInfoRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<FlightInfoModel> findByFlightInfoCode(String flightInfoCode) {
        FlightInfoModel flightInfoModel = jpaQueryFactory.select(flightInfoModel())
                .from(flightInfo)
                .where(flightInfo.state.eq(FlightInfoState.ABLED).and(flightInfo.code().eq(new FlightInfoCode(flightInfoCode))))
                .fetchFirst();
        return Optional.ofNullable(flightInfoModel);
    }

    private ConstructorExpression<FlightInfoModel> flightInfoModel() {
        return Projections.constructor(FlightInfoModel.class);
    }

}
