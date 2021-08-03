package com.ljy.flightreservation.flightInfo.infrastructure;

import com.ljy.flightreservation.core.infrastructure.QuerydslRepository;
import com.ljy.flightreservation.flightInfo.application.FlightInfoRepository;
import com.ljy.flightreservation.flightInfo.application.model.FlightInfoModel;
import com.ljy.flightreservation.flightInfo.application.model.FlightInfoSearch;
import com.ljy.flightreservation.flightInfo.domain.agg.FlightInfo;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ljy.flightreservation.flightInfo.domain.agg.QFlightInfo.flightInfo;

@Repository
public class QuerydslFlightInfoRepository extends QuerydslRepository<FlightInfo> implements FlightInfoRepository {
    @Override
    public List<FlightInfoModel> findAll(FlightInfoSearch search) {
       return jpaQueryFactory.select(flightInfoModel())
                .from(flightInfo)
                .where(eqDepartureArea(search.getDepartureArea()),
                        eqArrivalArea(search.getArrivalArea()))
                .limit(search.getSize())
                .offset(search.getSize() * search.getPage())
                .fetch();
    }

    private ConstructorExpression<FlightInfoModel> flightInfoModel() {
        return Projections.constructor(FlightInfoModel.class,
                flightInfo.code(),
                flightInfo.area(),
                flightInfo.times(),
                flightInfo.price,
                flightInfo.state,
                flightInfo.airplaneCode,
                flightInfo.needPassport);
    }

    private BooleanExpression eqDepartureArea(String departureArea) {
        if(Objects.isNull(departureArea)){
            return null;
        }
        return flightInfo.area().departureArea.eq(departureArea);
    }

    private BooleanExpression eqArrivalArea(String arrivalArea) {
        if(Objects.isNull(arrivalArea)){
            return null;
        }
        return flightInfo.area().arrivalArea.eq(arrivalArea);
    }
}
