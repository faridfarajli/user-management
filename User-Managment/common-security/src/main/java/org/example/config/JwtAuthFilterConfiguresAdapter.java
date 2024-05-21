package org.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class JwtAuthFilterConfiguresAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthRequestFilter jwtAuthRequestFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(jwtAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
