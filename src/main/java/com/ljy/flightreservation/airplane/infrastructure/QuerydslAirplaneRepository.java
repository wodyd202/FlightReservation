package com.ljy.flightreservation.airplane.infrastructure;

import com.ljy.flightreservation.airplane.application.AirplaneRepository;
import com.ljy.flightreservation.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.core.infrastructure.QuerydslRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.flightreservation.airplane.domain.agg.QAirplane.airplane;

@Repository
public class QuerydslAirplaneRepository extends QuerydslRepository<Airplane> implements AirplaneRepository {

    @Override
    public Optional<Airplane> findByAirplaneCode(AirplaneCode code) {
        Airplane findAirplane = jpaQueryFactory.selectFrom(airplane)
                .where(eqCode(code))
                .fetchFirst();
        return Optional.ofNullable(findAirplane);
    }

    private BooleanExpression eqCode(AirplaneCode code){
        return airplane.code().eq(code);
    }
}
