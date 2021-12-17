package com.ljy.flightreservation.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private JwtTokenResolver jwtTokenResolver;

    @Bean
    PasswordEncoder passwordEncoder(){
        return createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/member").authenticated()
                .antMatchers(HttpMethod.POST,"/api/member").permitAll()
                .antMatchers(HttpMethod.PATCH,"/api/member").authenticated()
                .antMatchers(HttpMethod.DELETE,"/api/member").authenticated()

                .antMatchers(HttpMethod.GET,"/api/airplane/**").permitAll()

                .antMatchers(HttpMethod.GET,"/api/reservation").authenticated()
                .antMatchers(HttpMethod.GET,"/api/reservation/**").authenticated()
                .antMatchers(HttpMethod.POST,"/api/reservation").authenticated()
            .antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
            .and()
        .addFilterBefore(new JwtAuthencationFilter(jwtTokenResolver,jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}

