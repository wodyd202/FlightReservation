package com.ljy.flightreservation.user.query.application;

import com.ljy.flightreservation.user.command.application.event.UserEvent;
import com.ljy.flightreservation.user.query.domain.QueryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.AccessException;

@Component
@Transactional
@Async
public class UserProjector {
    @Autowired private QueryUserRepository userRepository;

    private final String ON = "on";
    @EventListener
    protected void handle(UserEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QueryUser queryUser = userRepository.findByUserId(event.getId()).orElseGet(() -> new QueryUser());
        Method method = QueryUser.class.getDeclaredMethod(ON, event.getClass());
        method.setAccessible(true);
        method.invoke(queryUser, event);
        userRepository.save(queryUser);
    }
}
