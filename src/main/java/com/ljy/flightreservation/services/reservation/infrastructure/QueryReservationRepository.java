package com.ljy.flightreservation.services.reservation.infrastructure;

import com.ljy.flightreservation.services.reservation.application.model.ReservationModels;
import com.ljy.flightreservation.services.reservation.application.model.ReservationSearchDTO;
import com.ljy.flightreservation.services.reservation.domain.QReservation;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.ReservationRepository;
import com.ljy.flightreservation.services.reservation.domain.model.ReservationModel;
import com.ljy.flightreservation.services.reservation.domain.value.ReservationState;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ljy.flightreservation.services.reservation.domain.QReservation.reservation;

@Repository
@AllArgsConstructor
@Transactional
public class QueryReservationRepository implements ReservationRepository {
    @PersistenceContext private EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(Reservation reservation) {
        entityManager.persist(reservation);
    }

    // query

    @Override
    public Optional<Reservation> findByFlightSeqAndSitCode(long seq, SitInfo sitInfo) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(reservation)
                        .from(reservation)
                        .where(reservation.flightInfo().seq.eq(seq).and(reservation.sitInfo().eq(sitInfo)).and(reservation.state.eq(ReservationState.RESERVATE)))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<ReservationModel> findByIdAndBooker(long seq, String booker) {
        return Optional.ofNullable(
            jpaQueryFactory.select(Projections.constructor(ReservationModel.class,
                            reservation.seq,
                            reservation.flightInfo(),
                            reservation.price,
                            reservation.state,
                            reservation.reservationDate,
                            reservation.sitInfo(),
                            reservation.booker()
                    ))
                    .from(reservation)
                    .where(eqBooker(booker), eqSeq(seq))
                    .fetchFirst()
        );
    }

    @Override
    public List<ReservationModel> findAll(ReservationSearchDTO reservationSearchDTO, String booker) {
        return jpaQueryFactory.select(Projections.constructor(ReservationModel.class,
                        reservation.seq,
                        reservation.flightInfo(),
                        reservation.reservationDate,
                        reservation.booker()
                ))
                .from(reservation)
                .where(eqBooker(booker),
                        isPast(reservationSearchDTO.getPast()),
                        eqArrivalArea(reservationSearchDTO.getArrivalArea()),
                        eqState(reservationSearchDTO.getState()))
                .offset(reservationSearchDTO.getPage() * 20)
                .limit(20)
                .fetch();
    }

    @Override
    public long countAll(ReservationSearchDTO reservationSearchDTO, String booker) {
        return jpaQueryFactory.selectOne()
                .from(reservation)
                .where(eqBooker(booker),
                        isPast(reservationSearchDTO.getPast()),
                        eqArrivalArea(reservationSearchDTO.getArrivalArea()),
                        eqState(reservationSearchDTO.getState()))
                .fetchCount();
    }

    private BooleanExpression eqSeq(long seq) {
        return reservation.seq.eq(seq);
    }

    private BooleanExpression eqBooker(String booker){
        return reservation.booker().id.eq(booker);
    }

    private BooleanExpression isPast(Boolean past){
        if(past != null && past){
            return reservation.flightInfo().departureDate.lt(LocalDate.now());
        }
        return reservation.flightInfo().departureDate.goe(LocalDate.now());
    }

    private BooleanExpression eqArrivalArea(String area){
        if(!StringUtils.hasText(area)){
            return null;
        }
        return reservation.flightInfo().arrivalArea.eq(area);
    }

    private BooleanExpression eqState(ReservationState state){
        if(state == null){
            return null;
        }
        return reservation.state.eq(state);
    }
}
