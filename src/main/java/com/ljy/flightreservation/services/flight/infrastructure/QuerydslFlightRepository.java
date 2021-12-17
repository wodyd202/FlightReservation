package com.ljy.flightreservation.services.flight.infrastructure;

import com.ljy.flightreservation.services.flight.application.model.FlightSearchDTO;
import com.ljy.flightreservation.services.flight.domain.Flight;
import com.ljy.flightreservation.services.flight.domain.FlightRepository;
import com.ljy.flightreservation.services.flight.domain.model.FlightModel;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ljy.flightreservation.services.flight.domain.QFlight.flight;

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

    @Override
    public Optional<FlightModel> findById(Long flightSeq) {
        return Optional.ofNullable(
            jpaQueryFactory.select(Projections.constructor(FlightModel.class,
                            flight.seq,
                            flight.airplaneInfo(),
                            flight.flightDetail(),
                            flight.needPassport,
                            flight.state))
                    .from(flight)
                    .fetchFirst()
        );
    }

    @Override
    public List<FlightModel> findAll(FlightSearchDTO flightSearchDTO) {
        return jpaQueryFactory.select(Projections.constructor(FlightModel.class,
                        flight.seq,
                        flight.flightDetail(),
                        flight.needPassport,
                        flight.state))
                .from(flight)
                .where(isPast(flightSearchDTO.getPast()),
                        eqArrivalArea(flightSearchDTO.getPast(), flightSearchDTO.getArrivalArea()),
                        eqArrivalDate(flightSearchDTO.getPast(), flightSearchDTO.getArrivalDate()),
                        eqDepartureDate(flightSearchDTO.getPast(), flightSearchDTO.getDepartureDate()))
                .offset(flightSearchDTO.getPage() * 20)
                .limit(20)
                .fetch();
    }

    @Override
    public long countAll(FlightSearchDTO flightSearchDTO) {
        return jpaQueryFactory.selectOne()
                .from(flight)
                .where(isPast(flightSearchDTO.getPast()),
                        eqArrivalArea(flightSearchDTO.getPast(), flightSearchDTO.getArrivalArea()),
                        eqArrivalDate(flightSearchDTO.getPast(), flightSearchDTO.getArrivalDate()),
                        eqDepartureDate(flightSearchDTO.getPast(), flightSearchDTO.getDepartureDate()))
                .fetchCount();
    }

    private BooleanExpression isPast(Boolean past) {
        if(past != null && past){
            return flight.flightDetail().departureDate.lt(LocalDate.now());
        }
        return null;
    }

    private BooleanExpression eqDepartureDate(Boolean past, LocalDate departureDate) {
        if((past != null && past) || departureDate == null){
            return null;
        }
        return flight.flightDetail().departureDate.eq(departureDate);
    }

    private BooleanExpression eqArrivalDate(Boolean past, LocalDate arrivalDate){
        if((past != null && past) || arrivalDate == null){
            return null;
        }
        return flight.flightDetail().arrivalDate.eq(arrivalDate);
    }

    private BooleanExpression eqArrivalArea(Boolean past, String arrivalArea){
        if((past != null && past) || arrivalArea == null){
            return null;
        }
        return flight.flightDetail().arrivalArea.eq(arrivalArea);
    }
}
