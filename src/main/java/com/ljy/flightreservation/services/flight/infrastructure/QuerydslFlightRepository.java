package com.ljy.flightreservation.services.flight.infrastructure;

import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
@AllArgsConstructor
public class QuerydslFlightRepository implements FlightRepository {
    @PersistenceContext private EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Flight flight) {
        entityManager.persist(flight);
    }
}
