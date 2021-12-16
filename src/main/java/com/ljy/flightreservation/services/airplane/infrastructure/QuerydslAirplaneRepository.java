package com.ljy.flightreservation.services.airplane.infrastructure;

import com.ljy.flightreservation.services.airplane.domain.AirplaneRepository;
import com.ljy.flightreservation.services.airplane.domain.Airplane;
import com.ljy.flightreservation.services.airplane.domain.model.AirplaneModel;
import com.ljy.flightreservation.services.airplane.domain.value.AirplaneCode;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.flightreservation.services.airplane.domain.QAirplane.airplane;

@Repository
@AllArgsConstructor
@Transactional
public class QuerydslAirplaneRepository implements AirplaneRepository {
    @PersistenceContext private EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Airplane airplane) {
        entityManager.persist(airplane);
    }

    @Override
    public Optional<Airplane> findById(AirplaneCode code) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(airplane)
                .where(airplane.code().eq(code))
                .fetchFirst());
    }

    // query

    @Override
    public Optional<AirplaneModel> findById(String airplaneCode) {
        return Optional.ofNullable(jpaQueryFactory.select(Projections.constructor(AirplaneModel.class,
                        airplane.code(),
                        airplane.sitInfo(),
                        airplane.state
                    ))
                .from(airplane)
                .where(airplane.code().code.eq(airplaneCode))
                .fetchFirst());
    }
}
