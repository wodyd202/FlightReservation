package com.ljy.flightreservation.airplane.infrastructure;

import com.ljy.flightreservation.airplane.application.model.ReservationModel;
import com.ljy.flightreservation.airplane.application.ReservationRepository;
import com.ljy.flightreservation.airplane.application.model.ReservationSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuerydslReservationRepository implements ReservationRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationModel> findAll(ReservationSearch search) {
        return null;
    }
}
