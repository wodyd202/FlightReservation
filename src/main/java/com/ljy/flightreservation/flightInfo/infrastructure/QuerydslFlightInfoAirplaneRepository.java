package com.ljy.flightreservation.flightInfo.infrastructure;

import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import com.ljy.flightreservation.flightInfo.application.AirplaneRepository;
import com.ljy.flightreservation.flightInfo.application.model.Airplane;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ljy.flightreservation.airplane.domain.agg.QAirplane.airplane;

@Repository
@Transactional
public class QuerydslFlightInfoAirplaneRepository implements AirplaneRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Airplane> findByAirplaneCode(String airplaneCode) {
        Integer integer = jpaQueryFactory.selectOne()
                .from(airplane)
                .where(airplane.code().eq(new AirplaneCode(airplaneCode)))
                .fetchFirst();
        if(integer == null){
            return Optional.ofNullable(null);
        }else{
            return Optional.of(new Airplane(airplaneCode));
        }
    }
}
