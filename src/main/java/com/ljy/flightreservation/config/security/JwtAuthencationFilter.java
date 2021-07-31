package com.ljy.flightreservation.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthencationFilter implements Filter {
    private final JwtTokenResolver jwtTokenResolver;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthencationFilter(JwtTokenResolver jwtTokenResolver, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenResolver = jwtTokenResolver;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = jwtTokenResolver.resolve((HttpServletRequest) servletRequest);
            jwtTokenProvider.validation(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (EmptyTokenException e) {
            log.error("empty token");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidTokenException e) {
            log.error("invalid token");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
