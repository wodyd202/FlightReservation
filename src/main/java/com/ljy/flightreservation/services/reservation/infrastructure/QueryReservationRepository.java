package com.ljy.flightreservation.services.reservation.infrastructure;

import com.ljy.flightreservation.services.reservation.domain.QReservation;
import com.ljy.flightreservation.services.reservation.domain.Reservation;
import com.ljy.flightreservation.services.reservation.domain.ReservationRepository;
import com.ljy.flightreservation.services.reservation.domain.value.ReservationState;
import com.ljy.flightreservation.services.reservation.domain.value.SitInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
