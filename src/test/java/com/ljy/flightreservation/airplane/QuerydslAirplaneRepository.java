package com.ljy.flightreservation.airplane;

import com.ljy.flightreservation.airplane.command.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.command.domain.agg.QAirplane;
import com.ljy.flightreservation.airplane.command.domain.value.AirplaneCode;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.flightreservation.airplane.command.domain.agg.QAirplane.airplane;

@Repository
@Transactional
public class QuerydslAirplaneRepository implements AirplaneRepository{
    @PersistenceContext private EntityManager entityManager;
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Airplane> findByAirplaneCode(AirplaneCode code) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(airplane)
                                                    .where(airplane.code().eq(code))
                                                    .fetchFirst());
    }

    @Override
    public void save(Airplane airplane) {
        if(entityManager.contains(airplane)){
            entityManager.merge(airplane);
        }else{
            entityManager.persist(airplane);
        }
    }
}
