package com.ljy.flightreservation.airplane.infrastructure;

import com.ljy.flightreservation.airplane.application.AirplaneRepository;
import com.ljy.flightreservation.airplane.domain.agg.Airplane;
import com.ljy.flightreservation.airplane.domain.value.AirplaneCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.flightreservation.airplane.domain.agg.QAirplane.airplane;

@Repository
@Transactional
public class QuerydslAirplaneRepository implements AirplaneRepository {
    @PersistenceContext  private EntityManager entityManager;
    @Autowired  private JPAQueryFactory jpaQueryFactory;


    @Override
    public void save(Airplane airplane) {
        if(entityManager.contains(airplane)){
            entityManager.merge(airplane);
        }else{
            entityManager.persist(airplane);
        }
    }

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
