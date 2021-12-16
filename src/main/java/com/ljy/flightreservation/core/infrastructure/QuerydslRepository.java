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

    public void save(T member){
        if(entityManager.contains(member)){
            entityManager.merge(member);
        }else{
            entityManager.persist(member);
        }
    }
}
