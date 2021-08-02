package com.ljy.flightreservation.flightInfo;

import com.ljy.flightreservation.flightInfo.domain.agg.FlightInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class QuerydslFlightInfoRepository implements FlightInfoRepository{
    @PersistenceContext private EntityManager entityManager;

    @Override
    public void save(FlightInfo flightInfo) {
        if(entityManager.contains(flightInfo)){
            entityManager.merge(flightInfo);
        }else{
            entityManager.persist(flightInfo);
        }
    }
}
