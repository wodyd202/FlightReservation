package com.ljy.flightreservation.user.query.application;

import com.ljy.flightreservation.user.query.domain.QueryUser;
import com.ljy.flightreservation.user.query.domain.exception.UserNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class QueryUserService implements UserDetailsService {
    private final QueryUserRepository userRepository;

    public QueryUserService(QueryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public QueryUser findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()->new UserNotFoundException("user not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryUser user = userRepository.findByUserId(username).orElseThrow(()->new UsernameNotFoundException(username));
        return new User(username, user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }


}
