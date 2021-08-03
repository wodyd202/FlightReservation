package com.ljy.flightreservation.core.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class QuerydslRepository<T> {
    @PersistenceContext protected EntityManager entityManager;
    @Autowired protected JPAQueryFactory jpaQueryFactory;

    public void save(T obj){
        if(entityManager.contains(obj)){
            entityManager.merge(obj);
        }else{
            entityManager.persist(obj);
        }
    }
}
